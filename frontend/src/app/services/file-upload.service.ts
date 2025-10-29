/**
 * Modelo para el POST del documento.
 */
export interface FileUploadRequest {
  tipo: string;
  titulo: string;
  precio: string;
  documento: string;
}

/**
 * Modelo para el resultado del GET.
 */
export interface FileUploadResponse {
  id: number;
  tipo: string;
  titulo: string;
  precio: string;
  documento: string;
  etiquetas: string[];
  fechaSubida: string;
  tamanio: string;
}

import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, finalize, Observable, tap } from 'rxjs';

// Modelos de datos
export interface FileUploadRequest {
  tipo: string;
  titulo: string;
  precio: string;
  documento: string;
}

export interface FileUploadResponse {
  id: number;
  tipo: string;
  titulo: string;
  precio: string;
  documento: string;
  etiquetas: string[];
  fechaSubida: string;
  tamanio: string;
}

/**
 * Servicio para realizar operaciones de subida y obtención de ficheros.
 * Permite hacer GET para obtener archivos y POST para subir nuevos.
 */
@Injectable({
  providedIn: 'root',
})
export class FileUploadService {
  private postUrl =
    'https://curso-frida-team5.onrender.com/api/documentos/subir';
  private getUrl = 'https://curso-frida-team5.onrender.com/api/documentos';

  private http = inject(HttpClient);
  /**
   * Realiza una petición GET para obtener la lista de documentos subidos.
   * @returns Un observable con la lista de documentos.
   */
  getFiles(): Observable<FileUploadResponse[]> {
    return this.http.get<FileUploadResponse[]>(this.getUrl);
  }

  /**
   * Realiza una petición POST para subir un nuevo documento.
   * @param data Información del documento a subir (tipo, titulo, precio, documento).
   * @returns Un observable con la respuesta del servidor (puede cambiar según el backend).
   */
  uploadFile(data: FileUploadRequest): Observable<any> {
    console.log('Iniciando subida...');
    const startTime = Date.now(); // 1. Captura el tiempo de inicio

    return this.http.post<any>(this.postUrl, data, {reportProgress:true}).pipe(
      // 2. Operador 'tap' para registrar la respuesta exitosa
      tap((response) => {
        console.log('Respuesta del servidor (éxito):', response);
      }),

      // 3. (Opcional pero recomendado) Captura y registra errores
      catchError((error) => {
        console.error('Error en la subida:', error);
        // Re-lanza el error para que el componente también lo pueda gestionar
        throw error;
      }),

      // 4. Operador 'finalize' para medir el tiempo
      finalize(() => {
        // Esto se ejecutará siempre, al completarse o al dar error
        const duration = Date.now() - startTime;
        console.log(`Tiempo total de la petición: ${duration} ms`);
      })
    );
  }
}
