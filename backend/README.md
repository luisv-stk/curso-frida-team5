# Aplicación de Procesamiento de Documentos con LLM

## Descripción
Aplicación Spring Boot para subir documentos (fotografías, videos, ilustraciones, modelos 3D) y procesarlos con un LLM para extraer etiquetas descriptivas.

## Estructura del Proyecto

```
com.frida.example.demo/
├── controller/
│   └── DocumentoController.java          # Endpoints REST
├── model/
│   ├── Documento.java                    # Entidad JPA
│   └── TipoDocumento.java                # Enum de tipos
├── dto/
│   ├── DocumentoRequestDTO.java          # DTO de entrada
│   └── DocumentoResponseDTO.java         # DTO de salida
├── repository/
│   └── DocumentoRepository.java          # Repositorio JPA
├── service/
│   ├── DocumentoService.java             # Lógica de negocio
│   └── LLMService.java                   # Integración con LLM
└── DocumentoApplication.java             # Clase principal
```

## Tecnologías Utilizadas
- **Spring Boot 3.2.0**
- **Java 17**
- **Spring Data JPA**
- **H2 Database** (en memoria)
- **Maven**

## Endpoints Disponibles

### 1. Subir Documento
**POST** `/api/documentos/subir`

**Request Body:**
```json
{
  "tipoDocumento": 1,
  "titulo": "Paisaje de montaña",
  "precio": "29.99",
  "documento": "base64_encoded_image_data_here..."
}
```

**TipoDocumento:**
- 1 = Fotografía
- 2 = Video
- 3 = Ilustración
- 4 = 3D

**Response (201 Created):**
```json
{
  "tipo": 1,
  "titulo": "Paisaje de montaña",
  "precio": "29.99",
  "documento": "base64_encoded_image_data...",
  "etiquetas": ["paisaje", "naturaleza", "montaña", "cielo"],
  "fechaSubida": "2024-10-23T14:30:00",
  "tamanio": "100x600"
}
```

### 2. Obtener Todos los Documentos
**GET** `/api/documentos`

**Response (200 OK):**
```json
[
  {
    "tipo": 1,
    "titulo": "Paisaje de montaña",
    "precio": "29.99",
    "documento": "base64_encoded_image_data...",
    "etiquetas": ["paisaje", "naturaleza", "montaña", "cielo"],
    "fechaSubida": "2024-10-23T14:30:00",
    "tamanio": "100x600"
  },
  {
    "tipo": 3,
    "titulo": "Diseño abstracto",
    "precio": "15.50",
    "documento": "base64_encoded_image_data...",
    "etiquetas": ["ilustración", "diseño", "digital", "arte"],
    "fechaSubida": "2024-10-23T15:00:00",
    "tamanio": "100x600"
  }
]
```

## Base de Datos H2

### Configuración
La base de datos H2 está configurada en modo **in-memory**, lo que significa que los datos se pierden al reiniciar la aplicación.

**Acceso a la Consola H2:**
- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:documentosdb`
- Username: `sa`
- Password: *(dejar en blanco)*

### Esquema de Base de Datos

**Tabla `documentos`:**
| Campo | Tipo | Descripción |
|-------|------|-------------|
| id | BIGINT (PK) | Identificador único |
| tipo_documento | VARCHAR(50) | Tipo de documento (ENUM) |
| titulo | VARCHAR(255) | Título del documento |
| precio | VARCHAR(50) | Precio del documento |
| documento | CLOB | Documento en Base64 |
| tamanio | VARCHAR(50) | Tamaño calculado |
| fecha_subida | TIMESTAMP | Fecha de subida |

**Tabla `documento_etiquetas`:**
| Campo | Tipo | Descripción |
|-------|------|-------------|
| documento_id | BIGINT (FK) | Referencia al documento |
| etiqueta | VARCHAR(255) | Etiqueta descriptiva |

## Instalación y Ejecución

### Requisitos
- Java 17 o superior
- Maven 3.6 o superior

### Pasos

1. **Clonar o descargar el proyecto**

2. **Compilar el proyecto:**
```bash
mvn clean install
```

3. **Ejecutar la aplicación:**
```bash
mvn spring-boot:run
```

O ejecutar el JAR generado:
```bash
java -jar target/documento-app-1.0.0.jar
```

4. **Acceder a la aplicación:**
- API: `http://localhost:8080/api/documentos`
- Consola H2: `http://localhost:8080/h2-console`

## Integración con LLM

### Estado Actual
La implementación actual de `LLMService` contiene un **mock** que genera etiquetas de ejemplo. 

### Implementación Real
Para integrar un LLM real, debes modificar el método `extraerEtiquetas()` en `LLMService.java`. 

**Opciones recomendadas:**
1. **Anthropic Claude** (con capacidades de visión)
2. **OpenAI GPT-4 Vision**
3. **Google Gemini Vision**
4. **AWS Rekognition**
5. **Azure Computer Vision**

**Ejemplo de integración con Claude (comentado en el código):**
```java
// Ver LLMService.java para el código de ejemplo completo
// Necesitarás añadir tu API key en application.properties:
// anthropic.api.key=tu-api-key-aqui
```

## Pruebas con cURL

### Subir un documento:
```bash
curl -X POST http://localhost:8080/api/documentos/subir \
  -H "Content-Type: application/json" \
  -d '{
    "TipoDocumento": 1,
    "Titulo": "Test Image",
    "Precio": "10.00",
    "Documento": "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mNk+M9QDwADhgGAWjR9awAAAABJRU5ErkJggg=="
  }'
```

### Obtener todos los documentos:
```bash
curl -X GET http://localhost:8080/api/documentos
```

## Notas Importantes

1. **Tamaño de archivos:** La configuración actual permite archivos de hasta 50MB. Ajusta en `application.properties` si necesitas más.

2. **Base de datos:** H2 está en modo memoria. Para persistencia real, cambia a PostgreSQL, MySQL, etc.

3. **Seguridad:** Este es un ejemplo básico. En producción, añade:
   - Autenticación/Autorización (Spring Security)
   - Validación de entrada
   - Manejo de errores robusto
   - Rate limiting

4. **LLM:** Recuerda implementar la integración real con un servicio de LLM antes de usar en producción.

## Mejoras Futuras

- [ ] Implementar integración real con LLM
- [ ] Añadir paginación en el endpoint GET
- [ ] Implementar filtros de búsqueda
- [ ] Añadir validaciones más robustas
- [ ] Implementar caché para consultas frecuentes
- [ ] Añadir tests unitarios e integración
- [ ] Migrar a una base de datos persistente
- [ ] Implementar seguridad con Spring Security
- [ ] Añadir documentación con Swagger/OpenAPI

## Contacto y Soporte

Para preguntas o problemas, por favor crea un issue en el repositorio del proyecto.
