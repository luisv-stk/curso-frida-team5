# Requirements Specification

## 1. Overview  
This project is a Spring Boot demo application that uses an H2 database. By default, H2 runs in in-memory mode, so all data is lost when the server shuts down. The purpose of this enhancement is to configure H2 in file-based (persistent) mode so that data remains on disk and survives application restarts. Additionally, we will expose basic CRUD functionality over a REST API to demonstrate and verify persistence behavior.

---

## 2. Functional Requirements

### 2.1 Core Functionality  
MoSCoW Priority | Requirement  
--------------- | -----------  
Must Have       | Configure H2 to use a file-based database stored under `./data/demo-db`  
Must Have       | Expose REST endpoints for CRUD operations on a sample entity (`Item`)  
Should Have     | Provide a simple web-based UI (Thymeleaf or React) to list/add/edit/delete items  
Could Have      | Support database schema migration via Flyway or Liquibase  
Won’t Have      | Integration with an external production-ready RDBMS  

### 2.2 User Interactions  
- A client (e.g., Postman or browser) can call:  
  - `GET /api/items` – list all items  
  - `GET /api/items/{id}` – retrieve a single item  
  - `POST /api/items` – create a new item (JSON body)  
  - `PUT /api/items/{id}` – update an existing item  
  - `DELETE /api/items/{id}` – delete an item  
- If a web UI is implemented:  
  - A user opens `http://localhost:8080/` and sees a table of items.  
  - The user can add/edit/delete directly from the UI.  

### 2.3 Data Management  
- H2 must be configured in `application.properties` (or `application.yml`) to use `jdbc:h2:file:./data/demo-db` with `DB_CLOSE_ON_EXIT=FALSE`.  
- Entities are mapped via Spring Data JPA.  
- On application start, JPA will auto-create or validate tables for the `Item` entity.  
- Optionally, Flyway/Liquibase scripts initialize schema and insert test data.  

---

## 3. Non-Functional Requirements

### 3.1 Performance  
- **Response Time:**  
  - 95% of CRUD API calls return within 200 ms under a load of 50 concurrent users.  
- **Throughput:**  
  - System should handle at least 100 requests per second for basic CRUD.  
- **Scalability:**  
  - Must support vertical scaling (increasing JVM heap, CPU).  
  - H2 file mode is intended for development; no cluster mode required.

### 3.2 Security  
- **Authentication & Authorization:**  
  - Basic authentication with user/password defined in `application.properties` (development mode).  
  - All `/api/**` endpoints require authentication.  
- **Data Protection:**  
  - Database file permissions set so only the application user can read/write.  
- **Privacy Considerations:**  
  - No PII will be stored in the demo; if extended, all personal data must be encrypted at rest.

### 3.3 Usability  
- **User Experience:**  
  - Clear error messages in JSON for invalid requests.  
  - UI (if implemented) must be responsive for desktop browsers.  
- **Accessibility:**  
  - Follow basic WCAG 2.1 AA guidelines for form elements and tables.  
- **Browser/Platform Compatibility:**  
  - Support Chrome, Firefox, Edge (latest two versions).

### 3.4 Reliability  
- **Availability Target:**  
  - 99% uptime during working hours (08:00–18:00) for local development.  
- **Error Handling:**  
  - Graceful handling of database file lock errors.  
  - Return HTTP 5xx on unexpected exceptions with a JSON error payload.  
- **Backup & Recovery:**  
  - Documented procedure to back up the `./data/demo-db.mv.db` file.  
  - On startup, detect corrupted files and alert the user via logs.

---

## 4. User Stories

1. As a **developer**, I want to configure H2 in file-based mode so that data remains after shutting down the server.  
2. As an **API client**, I want to create a new `Item` via `POST /api/items` so that I can store data persistently.  
3. As an **API client**, I want to retrieve all items via `GET /api/items` so that I can display the stored data.  
4. As an **API client**, I want to update an existing item via `PUT /api/items/{id}` so that I can correct mistakes.  
5. As an **API client**, I want to delete an item via `DELETE /api/items/{id}` so that I can remove outdated records.  
6. As a **tester**, I want to stop and restart the application and still see previously created items so that persistence is verified.  
7. As an **administrator**, I want a documented backup procedure so that I can restore the database if it becomes corrupted.  
8. (Optional) As a **user**, I want a simple web UI to manage items so that I don’t need an API client.

---

## 5. Constraints and Assumptions

### 5.1 Technical Constraints  
- Must use Spring Boot 2.x or 3.x and Spring Data JPA.  
- Must use H2 database in file mode; no external RDBMS.  
- Java 11+ runtime environment.  
- No Docker/containerization requirement for MVP.

### 5.2 Business Constraints  
- Timeline: 2 sprints (4 weeks) to deliver core persistence functionality and CRUD APIs.  
- Budget: Development hours limited to existing team; no external consulting.  
- Resources: One full-stack developer, one QA engineer.

### 5.3 Assumptions  
- The local filesystem is writable and persistent across restarts.  
- Developers have JDK, Maven/Gradle installed.  
- Network access is not a blocking requirement for a local H2 file.  
- No multi-node clustering is required.  

---

## 6. Acceptance Criteria

- [ ] H2 is configured to use `jdbc:h2:file:./data/demo-db;DB_CLOSE_ON_EXIT=FALSE` in application properties.  
- [ ] Running `mvn spring-boot:run` (or equivalent) creates a file `./data/demo-db.mv.db`.  
- [ ] Creating an `Item` via `POST /api/items` and then restarting the application still returns that `Item` via `GET /api/items`.  
- [ ] All CRUD endpoints return the correct HTTP status codes (201 for create, 200 for success, 204 for delete, 404 for not found).  
- [ ] Error messages are returned in JSON format with meaningful `error` and `message` fields.  
- [ ] (If UI implemented) The web interface shows the current list of items and allows create/edit/delete operations.  
- [ ] Backup procedure documented in `README.md`.

---

## 7. Out of Scope

- Migration to production-grade databases (PostgreSQL, MySQL, etc.).  
- High-availability clustering or replication for H2.  
- Advanced security (OAuth2, JWT).  
- Containerization (Docker) or deployment pipelines.  
- Complex domain modeling—only a single `Item` entity is required for demonstration.