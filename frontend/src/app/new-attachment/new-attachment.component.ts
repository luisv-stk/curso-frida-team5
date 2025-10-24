import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Component, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { UploadLoaderComponent } from '../upload-loader/upload-loader.component';

@Component({
  selector: 'app-new-attachment',
  imports: [FormsModule, HttpClientModule, CommonModule, UploadLoaderComponent],
  templateUrl: './new-attachment.component.html',
  styleUrl: './new-attachment.component.css',
})
export class NewAttachmentComponent {
  http = inject(HttpClient);
  selectedFile: File | null = null;
  fileName: string = ''; // NUEVO: nombre del archivo
  fileType = 'Fotografía';
  title = '';
  price = '';
  file = null;

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

  /**
   * Abre el diálogo de selección de archivos
   */
  openFileInput(): void {
    const fileInput: HTMLElement | null = document.getElementById('fileInput');
    if (fileInput) fileInput.click();
  }

  /**
   * Limpia archivo y nombre
   */
  /*clearFile(): void {
    this.selectedFile = null;
    this.fileName = ''; // Limpiar nombre
    const fileInput: HTMLInputElement | null = document.getElementById(
      'fileInput'
    ) as HTMLInputElement;
    if (fileInput) fileInput.value = '';
  }*/

  /**
   * Lógica para subir archivo
   */
 /* uploadFile(): void {
    if (!this.selectedFile) return alert('Selecciona un archivo primero.');
    const formData = new FormData();
    formData.append('file', this.selectedFile);

    //this.http.post('/api/upload', formData).subscribe({});
  }*/

isLoading = false;
private uploadPercentage = 0;
private uploadInterval: any;
uploadProgress(): number {
  return this.uploadPercentage;
}

uploadFile(): void {
  if (!this.selectedFile) {
    alert('Selecciona un archivo primero.');
    return;
  }
  this.isLoading = true;
  this.uploadPercentage = 0;

  // Simula el progreso cada 80 ms
  this.uploadInterval = setInterval(() => {
    if (this.uploadPercentage < 100) {
      this.uploadPercentage += 5; // Incrementa en 5% cada vez
    } else {
      clearInterval(this.uploadInterval);
      this.isLoading = false;
      alert('Archivo subido correctamente (simulado)');
      // Puedes reiniciar el valor si quieres permitir otra subida
      // this.uploadPercentage = 0;
      // this.clearFile(); // O limpiar el archivo automáticamente
    }
  }, 80);
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
