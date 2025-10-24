-- Script SQL para crear las tablas en H2
-- Este script es OPCIONAL ya que JPA con ddl-auto=update las crea automáticamente

-- Tabla principal de documentos
CREATE TABLE IF NOT EXISTS documentos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tipo_documento VARCHAR(50) NOT NULL,
    titulo VARCHAR(255) NOT NULL,
    precio VARCHAR(50) NOT NULL,
    documento CLOB NOT NULL,
    tamanio VARCHAR(50),
    fecha_subida TIMESTAMP NOT NULL
);

-- Tabla de etiquetas (relación many-to-one)
CREATE TABLE IF NOT EXISTS documento_etiquetas (
    documento_id BIGINT NOT NULL,
    etiqueta VARCHAR(255),
    FOREIGN KEY (documento_id) REFERENCES documentos(id) ON DELETE CASCADE
);

-- Índices para mejorar el rendimiento
CREATE INDEX IF NOT EXISTS idx_documentos_tipo ON documentos(tipo_documento);
CREATE INDEX IF NOT EXISTS idx_documentos_fecha ON documentos(fecha_subida);
CREATE INDEX IF NOT EXISTS idx_etiquetas_documento ON documento_etiquetas(documento_id);
