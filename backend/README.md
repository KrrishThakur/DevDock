# DevDock Backend (Spring Boot + Spring Security + JWT + PostgreSQL)

The backend service for **DevDock** provides a secure, versioned RESTful API (`/api/v1/...`) with stateless JWT authentication, role-based access control, Hibernate/JPA object relational mapping, and Swagger OpenAPI documentation.

---

## 🛠️ Tech Stack & Architecture
- **Framework**: Spring Boot 3.3.x (Java 21/25)
- **Security**: Spring Security 6 + JSON Web Tokens (JJWT 0.12.x) + BCrypt password hashing
- **Persistence**: Spring Data JPA / Hibernate
- **Database**: PostgreSQL (Production/Docker) with out-of-the-box H2 PostgreSQL-compatible dev mode
- **Documentation**: Springdoc OpenAPI v3 / Swagger UI (`/swagger-ui.html`)
- **Build Tool**: Maven (`pom.xml` + Maven Wrapper)

---

## 🚀 Quick Start (Local Run)

### 1. Default Zero-Config Launch (Embedded Dev DB)
No local PostgreSQL installation required to start immediately:
```bash
# From the backend directory
./mvnw spring-boot:run
# On Windows PowerShell / Command Prompt:
.\mvnw.cmd spring-boot:run
```
The server will start on port `8080` and automatically seed realistic sample data (Users, Projects, Tasks, Resources, and Tags).

### 2. Running with PostgreSQL
Ensure PostgreSQL is running locally or via Docker:
```bash
docker run --name devdock-pg -e POSTGRES_DB=devdock -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -p 5432:5432 -d postgres:16-alpine
```

Start the backend with the `postgres` Spring profile:
```bash
# Maven run with postgres profile
./mvnw spring-boot:run -Dspring-boot.run.profiles=postgres
```

---

## ⚙️ Environment Variables & Configuration

| Variable | Default Value | Description |
|---|---|---|
| `PORT` | `8080` | HTTP server listening port |
| `SPRING_PROFILES_ACTIVE` | `default` (h2 dev) | Active profile (`default` or `postgres`) |
| `SPRING_DATASOURCE_URL` | `jdbc:h2:mem:devdock;MODE=PostgreSQL...` | JDBC database connection URL |
| `SPRING_DATASOURCE_USERNAME` | `sa` (or `postgres`) | Database user |
| `SPRING_DATASOURCE_PASSWORD` | `""` (or `postgres`) | Database password |
| `JWT_SECRET` | 256-bit base64 key | HMAC-SHA256 secret key for signing tokens |
| `JWT_EXPIRATION_MS` | `86400000` (24h) | Token validity in milliseconds |
| `CORS_ALLOWED_ORIGINS` | `http://localhost:5173,http://localhost:3000` | Allowed frontend origins |

---

## 📖 API Documentation & Swagger UI

Interactive Swagger documentation is available once the server is running at:
- **Swagger UI**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **OpenAPI JSON**: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

---

## 🔑 Default Seed Accounts

| Role | Email | Password |
|---|---|---|
| **User** | `demo@devdock.dev` | `password123` |
| **Admin** | `admin@devdock.dev` | `admin123` |
