# DevDock ⚓ — Centralized Developer Workspace & Learning Hub

**DevDock** is an end-to-end full-stack web application designed for software engineers and CS students to centralize their coding repositories, tasks, and learning resources (YouTube videos, GitHub repositories, official documentation, articles, and bookmarks) into a high-performance productivity dashboard.

---

## 🌟 Architecture & Tech Stack

```mermaid
graph TD
    Client[React 18 SPA (Vite + Tailwind CSS)] -->|REST API + JWT| Security[Spring Security 6 Filter Chain]
    Security -->|Validates Token| Controller[Thin REST Controllers (/api/v1/*)]
    Controller -->|DTOs| Service[@Service Business Logic Layer]
    Service -->|Ownership Check| Repo[Spring Data JPA Repositories]
    Repo -->|PostgreSQL / H2 Dev Mode| DB[(Relational Database)]
```

### **Frontend**
- **Framework**: React 18 (Vite, Functional Components + Custom Hooks)
- **Styling**: Tailwind CSS (Dark slate developer theme, responsive mobile-ready layout)
- **Networking**: Axios with centralized JWT request interceptors and 401 response handling
- **Routing**: React Router v6 with `PrivateRoute` authentication guards
- **Icons**: Lucide React

### **Backend**
- **Framework**: Spring Boot 3.3.x (Java 21/25)
- **Security**: Spring Security 6 with stateless JWT (`jjwt` 0.12.x) and BCrypt password encryption
- **Data & Persistence**: Spring Data JPA / Hibernate on normalized PostgreSQL
- **Dev DB Mode**: Out-of-the-box in-memory PostgreSQL-compatible mode for instant zero-dependency launch
- **API Documentation**: Springdoc OpenAPI v3 / Swagger UI (`/swagger-ui.html`)
- **Build Management**: Maven (`pom.xml` + included `mvnw` / `mvnw.cmd`)

---

## 🚀 Quick Start Guide (Run Locally in 2 Minutes)

### Prerequisites
- **Java**: Java 17, 21, or 25 LTS installed
- **Node.js**: Node.js v18+ and npm installed

---

### Step 1: Start the Backend

1. Open a terminal and navigate to the `backend` directory:
   ```bash
   cd backend
   ```
2. Run the Spring Boot application using the Maven wrapper:
   - **On Linux / macOS**:
     ```bash
     ./mvnw spring-boot:run
     ```
   - **On Windows (PowerShell / CMD)**:
     ```powershell
     .\mvnw.cmd spring-boot:run
     ```

> **Note**: By default, the backend automatically initializes and seeds realistic demo data into an embedded, high-performance PostgreSQL-compatible in-memory store.
> 
> To run against a live PostgreSQL instance, start Postgres (via `docker compose up -d`) and start with profile:
> `.\mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=postgres`

Backend will be accessible at **`http://localhost:8080`**.
Swagger OpenAPI interactive UI will be available at **`http://localhost:8080/swagger-ui.html`**.

---

### Step 2: Start the Frontend

1. Open a new terminal and navigate to the `frontend` directory:
   ```bash
   cd frontend
   ```
2. Install npm dependencies:
   ```bash
   npm install
   ```
3. Start the Vite development server:
   ```bash
   npm run dev
   ```

Frontend will be accessible at **`http://localhost:5173`**.

---

## 🔑 Pre-Seeded Demo Accounts

The database comes pre-seeded with rich, realistic developer projects, tasks, and bookmarks so you can explore immediately:

| Account | Email | Password | Role |
|---|---|---|---|
| **Demo User** | `demo@devdock.dev` | `password123` | `ROLE_USER` |
| **Admin User** | `admin@devdock.dev` | `admin123` | `ROLE_ADMIN` |

> 💡 **Tip**: On the login page, you can click the **"1-Click Demo Account"** button to log in instantly without typing credentials!

---

## 📊 Core Features Implemented

1. **Authentication & Security**
   - User registration and login (`/api/v1/auth/*`).
   - Secure BCrypt password hashing.
   - Stateless HMAC-SHA256 JWT tokens passed as `Authorization: Bearer <token>`.
   - Strict ownership checks at the `@Service` layer so users only access their own data.

2. **Project Management (Full CRUD)**
   - Create, update, view, and delete projects.
   - Links to GitHub repositories and live deployments.
   - Real-time progress bar calculation based on completed vs total tasks.

3. **Task Manager (Full CRUD)**
   - Interactive Kanban Board (TODO / IN PROGRESS / DONE).
   - Filterable List Table view.
   - Priority indicators (`LOW`, `MEDIUM`, `HIGH`, `URGENT`) and due date tracking.
   - 1-click status switcher (`Move` / checkbox).

4. **Resource & Bookmark Manager**
   - Save learning bookmarks categorized by type (`YOUTUBE`, `GITHUB`, `DOCUMENTATION`, `ARTICLE`, `OTHER`).
   - Optional association to specific project tasks.
   - Tagging system with tag pill filters and quick URL copying.

5. **Centralized Dashboard**
   - Aggregate metric cards (Total Projects, Tasks, Completion Rate %, Bookmarks).
   - Visual task status breakdown chart.
   - Recent projects overview with progress bars.
   - Upcoming high-priority task focus widget.

6. **User Profile & Settings**
   - Edit name, avatar URL, and developer bio.
   - Change password form with validation.

---

## 📡 REST API Catalog

| Method | Endpoint | Description | Auth |
|---|---|---|---|
| `POST` | `/api/v1/auth/register` | Register a new user | Public |
| `POST` | `/api/v1/auth/login` | Login and receive Bearer JWT | Public |
| `GET` | `/api/v1/users/me` | Fetch logged-in user profile | Bearer JWT |
| `PUT` | `/api/v1/users/me` | Update name, avatar, bio | Bearer JWT |
| `PUT` | `/api/v1/users/me/password` | Change user password | Bearer JWT |
| `GET` | `/api/v1/projects` | List projects (search & status filters) | Bearer JWT |
| `POST` | `/api/v1/projects` | Create a new project | Bearer JWT |
| `GET` | `/api/v1/projects/{id}` | Get project details by ID | Bearer JWT |
| `PUT` | `/api/v1/projects/{id}` | Update project details | Bearer JWT |
| `DELETE` | `/api/v1/projects/{id}` | Delete project (cascades tasks) | Bearer JWT |
| `GET` | `/api/v1/projects/{id}/tasks` | Get all tasks for a project | Bearer JWT |
| `POST` | `/api/v1/projects/{id}/tasks` | Create a task for a project | Bearer JWT |
| `GET` | `/api/v1/tasks` | List all tasks across projects (filters) | Bearer JWT |
| `GET` | `/api/v1/tasks/{id}` | Get task details by ID | Bearer JWT |
| `PUT` | `/api/v1/tasks/{id}` | Update task details | Bearer JWT |
| `PATCH` | `/api/v1/tasks/{id}/status` | Quick status change | Bearer JWT |
| `DELETE` | `/api/v1/tasks/{id}` | Delete a task | Bearer JWT |
| `GET` | `/api/v1/resources` | List resources (search, type, tag filters) | Bearer JWT |
| `POST` | `/api/v1/resources` | Create bookmark with tags & task link | Bearer JWT |
| `GET` | `/api/v1/resources/{id}` | Get resource by ID | Bearer JWT |
| `PUT` | `/api/v1/resources/{id}` | Update resource bookmark | Bearer JWT |
| `DELETE` | `/api/v1/resources/{id}` | Delete resource bookmark | Bearer JWT |
| `GET` | `/api/v1/tags` | List all tags created by user | Bearer JWT |
| `GET` | `/api/v1/dashboard/summary` | Get aggregated dashboard metrics | Bearer JWT |

---

## 🧪 Postman Collection
Import `devdock-postman-collection.json` into Postman to test all endpoints. The `Login` request automatically populates the `jwtToken` variable for all subsequent requests!
