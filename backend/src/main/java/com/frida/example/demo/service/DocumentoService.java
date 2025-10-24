package com.frida.example.demo.service;

import com.frida.example.demo.dto.DocumentoRequestDTO;
import com.frida.example.demo.dto.DocumentoResponseDTO;
import com.frida.example.demo.model.Documento;
import com.frida.example.demo.model.TipoDocumento;
import com.frida.example.demo.repository.DocumentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

@Service
public class DocumentoService {

    @Autowired
    private DocumentoRepository documentoRepository;

    @Autowired
    private LLMService llmService;

    /**
     * Procesa el documento: calcula tamaño, llama al LLM para obtener etiquetas y
     * guarda en BD
     */
    @Transactional
    public DocumentoResponseDTO procesarDocumento(DocumentoRequestDTO requestDTO) {
        // Convertir tipo de documento
        TipoDocumento tipo = TipoDocumento.fromCodigo(requestDTO.getTipoDocumento());

        // Calcular tamaño del documento (Base64)
        String tamanio = calcularDimensiones(requestDTO.getDocumento());

        // Crear entidad
        Documento documento = new Documento(
                tipo,
                requestDTO.getTitulo(),
                requestDTO.getPrecio(),
                requestDTO.getDocumento(),
                tamanio);

        // Procesar con LLM para obtener etiquetas
        List<String> etiquetas = llmService.extraerEtiquetas(requestDTO.getDocumento(), tipo);
        documento.setEtiquetas(etiquetas);

        // Guardar en base de datos
        Documento documentoGuardado = documentoRepository.save(documento);

        // Convertir a DTO de respuesta
        return convertirAResponseDTO(documentoGuardado);
    }

    /**
     * Obtiene todos los documentos de la base de datos
     */
    @Transactional(readOnly = true)
    public List<DocumentoResponseDTO> obtenerTodosDocumentos() {
        List<Documento> documentos = documentoRepository.findAll();
        return documentos.stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Calcula el tamaño del archivo base64 en formato legible
     */
    private String calcularTamanio(String base64) {
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(base64);
            long bytes = decodedBytes.length;

            if (bytes < 1024) {
                return bytes + " B";
            } else if (bytes < 1024 * 1024) {
                return String.format("%.2f KB", bytes / 1024.0);
            } else {
                return String.format("%.2f MB", bytes / (1024.0 * 1024.0));
            }
        } catch (Exception e) {
            return "Desconocido";
        }
    }

    /**
     * Calcula las dimensiones de la imagen en formato WxH (ej: 1920x1080)
     */
    private String calcularDimensiones(String base64) {
        try {
            // Decodificar base64
            byte[] imageBytes = Base64.getDecoder().decode(base64);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);

            // Leer la imagen
            BufferedImage image = ImageIO.read(bis);

            if (image != null) {
                int width = image.getWidth();
                int height = image.getHeight();
                return width + "x" + height + " px";
            } else {
                return "";
            }
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Convierte una entidad Documento a DTO de respuesta
     */
    private DocumentoResponseDTO convertirAResponseDTO(Documento documento) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        return new DocumentoResponseDTO(
                documento.getId(),
                documento.getTipoDocumento().getCodigo(),
                documento.getTitulo(),
                documento.getPrecio(),
                documento.getDocumento(),
                documento.getEtiquetas(),
                documento.getFechaSubida().format(formatter),
                documento.getTamanio());
    }
}
