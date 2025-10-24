package com.frida.example.demo.model;

public enum TipoDocumento {
    FOTOGRAFIA("1"),
    VIDEO("2"),
    ILUSTRACION("3"),
    TRES_D("4");

    private final String codigo;

    TipoDocumento(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public static TipoDocumento fromCodigo(String codigo) {
        for (TipoDocumento tipo : TipoDocumento.values()) {
            if (tipo.getCodigo().equals(codigo)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Código de tipo de documento no válido: " + codigo);
    }
}
