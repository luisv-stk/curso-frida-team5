import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Component, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { UploadLoaderComponent } from '../upload-loader/upload-loader.component';
import {
  FileUploadRequest,
  FileUploadService,
} from '../../services/file-upload.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-new-attachment',
  imports: [FormsModule, CommonModule, UploadLoaderComponent, HttpClientModule],
  templateUrl: './new-attachment.component.html',
  styleUrl: './new-attachment.component.css',
})
export class NewAttachmentComponent {
  http = inject(HttpClient);
  selectedFile: File | null = null;
  fileName: string = '';
  fileType = 'Fotografía';
  title = '';
  price = '';
  file = null;
  fileUploadService = inject(FileUploadService);
  router = inject(Router);
  /**
   * Evento en el cambio de tipo de archivo
   */
  onFileTypeChange(event: any): void {
    this.fileType = event.target.value;
  }

  /**
   * Captura el archivo seleccionado y guarda el nombre
   */
  onFileChange(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      const file = input.files[0];
      // Validar tamaño y tipo
      if (
        file.size <= 2 * 1024 * 1024 &&
        (file.type === 'image/jpeg' || file.type === 'image/png')
      ) {
        this.selectedFile = file;
        this.fileName = file.name; // Guardar nombre
      } else {
        alert('Archivo no válido.');
        this.selectedFile = null;
        this.fileName = ''; // Limpiar nombre
      }
    } else {
      this.selectedFile = null;
      this.fileName = ''; // Limpiar nombre si no hay selección
    }
  }

  openFileInput(): void {
    const fileInput: HTMLElement | null = document.getElementById('fileInput');
    if (fileInput) fileInput.click();
  }

  isLoading = false;
  private uploadPercentage = 0;
  private uploadInterval: any;
  uploadProgress(): number {
    return this.uploadPercentage;
  }

  fileToBase64(file: File): Promise<string> {
    return new Promise<string>((resolve, reject) => {
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => {
        // Resultado: data:[..];base64,[base64string]
        const result = reader.result as string;
        // Quitar el prefijo "data:.....;base64," (opcional, según tu API)
        const base64 = result.split(',')[1];
        resolve(base64);
      };
      reader.onerror = (error) => reject(error);
    });
  }

  async uploadFile(): Promise<void> {
    if (!this.selectedFile) {
      alert('Selecciona un archivo primero.');
      return;
    }

    // Convertir a base64 de forma asíncrona
    let base64String: string;
    try {
      base64String = await this.fileToBase64(this.selectedFile);
    } catch (error) {
      alert('Error al convertir el archivo a Base64.');
      return;
    }

    const dataToUpload: FileUploadRequest = {
      tipo: '1',
      titulo: this.title,
      precio: this.price,
      documento: base64String,
    };

    this.isLoading = true;
    this.uploadPercentage = 0;

    this.fileUploadService.uploadFile(dataToUpload).subscribe({
      next: (response) => {
        this.isLoading = false;
        alert('Archivo subido correctamente');
        console.log('Respuesta recibida en el COMPONENTE:', response);
        this.clearFile();
        this.router.navigate(['/items-list'], {
          state: { uploadResponse: response },
        });
      },
      error: (error) => {
        this.isLoading = false;
        console.error('Error recibido en el COMPONENTE:', error);
      },
    });
  }

  clearFile(): void {
    this.selectedFile = null;
    this.fileName = '';
    this.uploadPercentage = 0;
    const fileInput: HTMLInputElement | null = document.getElementById(
      'fileInput'
    ) as HTMLInputElement;
    if (fileInput) fileInput.value = '';
  }

  /*uploadProgress = signal(35); // Empezamos en 35% como ejemplo
  currentFile = signal('Retrato_male.JPG');
  sizeInfo = signal('-1,2 Gb.');
  isLoading = false;*/
  // Método para actualizar el progreso desde el slider
  /*onProgressChange(event: Event) {
    const value = (event.target as HTMLInputElement).value;
    this.uploadProgress.set(Number(value));

    // Lógica de ejemplo para actualizar el texto
    if (this.uploadProgress() === 100) {
      this.isLoading = false;
      this.sizeInfo.set('¡Completado!');
    } else {
      this.isLoading = true;
      this.sizeInfo.set('-1,2 Gb.');
    }
  }*/
}
