package com.frida.example.demo.service;

import com.frida.example.demo.model.TipoDocumento;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class LLMService {

    @Value("${frida.llm.api.url:https://frida-llm-api.azurewebsites.net/v1/chat/completions}")
    private String apiUrl;

    @Value("${frida.llm.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public LLMService() {
        this.restTemplate = new RestTemplate();
    }

    /**
     * Extrae etiquetas de una imagen usando FRIDA LLM API (Claude)
     */
    public List<String> extraerEtiquetas(String imagenBase64, TipoDocumento tipo) {
        try {
            // Preparar headers
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + apiKey);
            headers.set("accept", "application/json");
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Construir el prompt según el tipo de documento
            String prompt = construirPrompt(tipo);

            // Construir el body de la petición
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "claude-4-sonnet");
            requestBody.put("stream", false);
            requestBody.put("enable_caching", true);

            // Construir el mensaje
            List<Map<String, Object>> messages = new ArrayList<>();
            Map<String, Object> userMessage = new HashMap<>();
            userMessage.put("role", "user");

            // Construir el contenido (texto + imagen)
            List<Map<String, Object>> content = new ArrayList<>();

            // 1. Añadir el prompt de texto
            Map<String, Object> textContent = new HashMap<>();
            textContent.put("type", "text");
            textContent.put("text", prompt);
            content.add(textContent);

            // 2. Añadir la imagen
            Map<String, Object> imageContent = new HashMap<>();
            imageContent.put("type", "image_url");

            Map<String, String> imageUrl = new HashMap<>();
            imageUrl.put("url", imagenBase64);
            imageUrl.put("detail", "auto");

            imageContent.put("image_url", imageUrl);
            content.add(imageContent);

            userMessage.put("content", content);
            messages.add(userMessage);
            requestBody.put("messages", messages);

            // Hacer la petición
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
            ResponseEntity<Map> response = restTemplate.postForEntity(apiUrl, request, Map.class);

            // Procesar la respuesta
            return procesarRespuesta(response.getBody());

        } catch (Exception e) {
            // En caso de error, retornar etiquetas por defecto
            System.err.println("Error al llamar a FRIDA LLM API: " + e.getMessage());
            e.printStackTrace();
            return generarEtiquetasPorDefecto(tipo);
        }
    }

    /**
     * Construye el prompt según el tipo de documento
     */
    private String construirPrompt(TipoDocumento tipo) {
        String tipoStr;
        switch (tipo) {
            case FOTOGRAFIA:
                tipoStr = "fotografía";
                break;
            case VIDEO:
                tipoStr = "video";
                break;
            case ILUSTRACION:
                tipoStr = "ilustración";
                break;
            case TRES_D:
                tipoStr = "modelo 3D";
                break;
            default:
                tipoStr = "imagen";
        }

        return "Analiza esta " + tipoStr + " y proporciona entre 5 y 10 etiquetas descriptivas relevantes en español. "
                +
                "Cada etiqueta DEBE ser UNA SOLA PALABRA (sin espacios). " +
                "Las etiquetas deben describir:\n" +
                "- El tema principal\n" +
                "- Los elementos visuales\n" +
                "- El estilo\n" +
                "- Los colores\n" +
                "- El ambiente\n\n" +
                "IMPORTANTE: Responde ÚNICAMENTE con palabras individuales separadas por comas, sin frases, sin espacios en las etiquetas, sin numeración. "
                +
                "Ejemplo correcto: paisaje, montaña, atardecer, naturaleza, naranja, tranquilo, exterior, cielo\n" +
                "Ejemplo INCORRECTO: paisaje de montaña, cielo naranja";
    }

    /**
     * Procesa la respuesta de la API y extrae las etiquetas
     */
    private List<String> procesarRespuesta(Map<String, Object> responseBody) {
        try {
            // Navegar por la estructura de respuesta
            List<Map<String, Object>> choices = (List<Map<String, Object>>) responseBody.get("choices");

            if (choices != null && !choices.isEmpty()) {
                Map<String, Object> firstChoice = choices.get(0);
                Map<String, Object> message = (Map<String, Object>) firstChoice.get("message");
                String content = (String) message.get("content");

                // Extraer etiquetas del contenido
                return extraerEtiquetasDeTexto(content);
            }
        } catch (Exception e) {
            System.err.println("Error al procesar respuesta: " + e.getMessage());
        }

        return new ArrayList<>();
    }

    /**
     * Extrae etiquetas del texto de respuesta del LLM
     */
    private List<String> extraerEtiquetasDeTexto(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return new ArrayList<>();
        }

        // Buscar si hay una línea con etiquetas separadas por comas
        String[] lineas = texto.split("\n");

        for (String linea : lineas) {
            linea = linea.trim();

            // Si la línea contiene comas y no es muy larga (probablemente sea la lista de
            // etiquetas)
            if (linea.contains(",") && linea.length() < 300) {
                return Arrays.stream(linea.split(","))
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .filter(s -> s.length() > 2) // Filtrar etiquetas muy cortas
                        .limit(10) // Máximo 10 etiquetas
                        .collect(Collectors.toList());
            }
        }

        // Si no encuentra el formato esperado, intentar extraer palabras clave
        return Arrays.stream(texto.split("[,\\.\\n]"))
                .map(String::trim)
                .filter(s -> !s.isEmpty() && s.length() > 2)
                .limit(10)
                .collect(Collectors.toList());
    }

    /**
     * Genera etiquetas por defecto en caso de error
     */
    private List<String> generarEtiquetasPorDefecto(TipoDocumento tipo) {
        List<String> etiquetas = new ArrayList<>();

        switch (tipo) {
            case FOTOGRAFIA:
                etiquetas.addAll(Arrays.asList("fotografía", "imagen", "visual"));
                break;
            case VIDEO:
                etiquetas.addAll(Arrays.asList("video", "multimedia", "audiovisual"));
                break;
            case ILUSTRACION:
                etiquetas.addAll(Arrays.asList("ilustración", "diseño", "arte digital"));
                break;
            case TRES_D:
                etiquetas.addAll(Arrays.asList("3D", "modelado", "render"));
                break;
        }

        return etiquetas;
    }
}