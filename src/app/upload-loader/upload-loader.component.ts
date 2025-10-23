import { Component, computed, input } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-upload-loader',
  standalone: true,
  imports: [CommonModule], // Necesario para [class.active] y @for
  templateUrl: './upload-loader.component.html',
  styleUrls: ['./upload-loader.component.css']
})
export class UploadLoaderComponent {
  // --- INPUTS (Signals) ---
  // El progreso de la subida, de 0 a 100
  public progress = input<number>(0);
  // El nombre del archivo
  public fileName = input<string>('Archivo.jpg');
  // La información del tamaño (ej: "-1,2 Gb.")
  public fileInfo = input<string>('0 Gb.');

  // --- LÓGICA INTERNA ---
  // Total de barras de progreso que queremos mostrar
  private totalBars = 12;

  // Un array simple para poder iterar con @for en la plantilla
  public bars = Array(this.totalBars).fill(0);

  // Signal "computada" que calcula cuántas barras deben estar activas
  public activeBarsCount = computed(() => {
    const progressValue = this.progress(); // Obtenemos el valor del signal

    // Si el progreso es 100% o más, activa todas las barras
    if (progressValue >= 100) {
      return this.totalBars;
    }

    // Calcula cuántas barras deben estar activas
    const barsToActivate = Math.floor(progressValue / (100 / this.totalBars));
    return barsToActivate;
  });
}
