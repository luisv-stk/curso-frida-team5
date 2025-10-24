package com.frida.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "documentos")
public class Documento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_documento", nullable = false)
    private TipoDocumento tipoDocumento;

    @Column(name = "titulo", nullable = false, length = 255)
    private String titulo;

    @Column(name = "precio", nullable = false, length = 50)
    private String precio;

    @Lob
    @Column(name = "documento", nullable = false, columnDefinition = "CLOB")
    private String documento; // Base64

    @Column(name = "tamanio", length = 50)
    private String tamanio;

    @Column(name = "fecha_subida", nullable = false)
    private LocalDateTime fechaSubida;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "documento_etiquetas", 
                    joinColumns = @JoinColumn(name = "documento_id"))
    @Column(name = "etiqueta")
    private List<String> etiquetas = new ArrayList<>();

    // Constructores
    public Documento() {
        this.fechaSubida = LocalDateTime.now();
    }

    public Documento(TipoDocumento tipoDocumento, String titulo, String precio, 
                     String documento, String tamanio) {
        this.tipoDocumento = tipoDocumento;
        this.titulo = titulo;
        this.precio = precio;
        this.documento = documento;
        this.tamanio = tamanio;
        this.fechaSubida = LocalDateTime.now();
        this.etiquetas = new ArrayList<>();
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
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

    public String getTamanio() {
        return tamanio;
    }

    public void setTamanio(String tamanio) {
        this.tamanio = tamanio;
    }

    public LocalDateTime getFechaSubida() {
        return fechaSubida;
    }

    public void setFechaSubida(LocalDateTime fechaSubida) {
        this.fechaSubida = fechaSubida;
    }

    public List<String> getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(List<String> etiquetas) {
        this.etiquetas = etiquetas;
    }
}
