package com.frida.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DocumentoRequestDTO {

    @JsonProperty("tipo")
    private String tipoDocumento; // Fotografía, Video, Ilustración, 3D

    @JsonProperty("titulo")
    private String titulo;

    @JsonProperty("precio")
    private String precio;

    @JsonProperty("documento")
    private String documento; // Base64

    // Constructores
    public DocumentoRequestDTO() {
    }

    public DocumentoRequestDTO(String tipoDocumento, String titulo, String precio, String documento) {
        this.tipoDocumento = tipoDocumento;
        this.titulo = titulo;
        this.precio = precio;
        this.documento = documento;
    }

    // Getters y Setters
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
}
