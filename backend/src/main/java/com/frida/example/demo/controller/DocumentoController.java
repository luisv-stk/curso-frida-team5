package com.frida.example.demo.controller;

import com.frida.example.demo.dto.DocumentoRequestDTO;
import com.frida.example.demo.dto.DocumentoResponseDTO;
import com.frida.example.demo.service.DocumentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/documentos")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS})
@Tag(name = "Documentos", description = "API para el procesamiento y gestión de documentos")
public class DocumentoController {

    @Autowired
    private DocumentoService documentoService;

    /**
     * Endpoint 1: Subir imagen y procesar con LLM
     * POST /api/documentos/subir
     */
    @Operation(summary = "Procesar documento", 
               description = "Recibe un documento en formato base64, lo procesa usando un LLM y retorna la información extraída")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Documento procesado exitosamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/subir")
    public ResponseEntity<DocumentoResponseDTO> subirDocumento(
            @RequestBody DocumentoRequestDTO requestDTO) {
        try {
            DocumentoResponseDTO response = documentoService.procesarDocumento(requestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Endpoint 2: Obtener listado de todos los documentos procesados
     * GET /api/documentos
     */
    @Operation(summary = "Obtener todos los documentos", 
               description = "Retorna la lista completa de documentos procesados almacenados en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de documentos obtenida exitosamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<List<DocumentoResponseDTO>> obtenerTodosDocumentos() {
        try {
            List<DocumentoResponseDTO> documentos = documentoService.obtenerTodosDocumentos();
            return ResponseEntity.ok(documentos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
