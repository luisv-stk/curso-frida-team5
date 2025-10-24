package com.frida.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class DocumentoResponseDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("tipo")
    private String tipoDocumento;

    @JsonProperty("titulo")
    private String titulo;

    @JsonProperty("precio")
    private String precio;

    @JsonProperty("documento")
    private String documento; // Base64

    @JsonProperty("etiquetas")
    private List<String> etiquetas;

    @JsonProperty("fechaSubida")
    private String fechaSubida; // Formato ISO 8601

    @JsonProperty("tamanio")
    private String tamanio;

    // Constructores
    public DocumentoResponseDTO() {
    }

    public DocumentoResponseDTO(Long id, String tipoDocumento, String titulo, String precio, 
                               String documento, List<String> etiquetas, 
                               String fechaSubida, String tamanio) {
        this.id = id;
        this.tipoDocumento = tipoDocumento;
        this.titulo = titulo;
        this.precio = precio;
        this.documento = documento;
        this.etiquetas = etiquetas;
        this.fechaSubida = fechaSubida;
        this.tamanio = tamanio;
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public List<String> getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(List<String> etiquetas) {
        this.etiquetas = etiquetas;
    }

    public String getFechaSubida() {
        return fechaSubida;
    }

    public void setFechaSubida(String fechaSubida) {
        this.fechaSubida = fechaSubida;
    }

    public String getTamanio() {
        return tamanio;
    }

    public void setTamanio(String tamanio) {
        this.tamanio = tamanio;
    }
}
