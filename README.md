# TaskFlow

A task management REST API built with **Spring Boot 4** and **Java 25**, following **Hexagonal Architecture** (Ports & Adapters) principles.

## Tech Stack

- **Java 25** / **Spring Boot 4.0.4**
- **Spring Data JPA** + **PostgreSQL**
- **Spring Validation** (Jakarta Bean Validation)
- **Spring Async Events** for notifications
- **Maven** build tool

## Architecture

The project follows a Hexagonal / Clean Architecture layout with two bounded contexts:

```
src/main/java/com/sonny/taskflow/
├── task/                              # Task bounded context
│   ├── domain/
│   │   ├── entity/        Task, TaskStatus
│   │   ├── event/         TaskCompletedEvent
│   │   ├── exception/     TaskNotFoundException
│   │   ├── port/          TaskRepositoryPort (interface)
│   │   └── usecase/       CreateTaskUseCase, CompleteTaskUseCase, GetTasksUseCase
│   ├── application/
│   │   ├── controller/    TaskController (REST)
│   │   ├── dto/           CreateTaskRequest, TaskResponse
│   │   └── handler/       TaskExceptionHandler
│   └── infrastructure/
│       ├── config/        TaskConfig (bean wiring)
│       └── persistence/   TaskJpaEntity, TaskJpaRepository, TaskRepositoryAdapter
│
├── notification/                      # Notification bounded context
│   ├── domain/usecase/    NotifyUserUseCase, NotifyCommand
│   ├── application/listener/ TaskEventListener
│   └── infrastructure/config/ NotificationConfig (async executor)
│
└── TaskFlowApplication.java
```

**Key design decisions:**
- Domain layer has zero Spring dependencies (pure Java)
- Use cases are wired as Spring beans via `@Configuration` classes
- JPA entities are separated from domain entities (mapped via `toDomain()`/`fromDomain()`)
- Events decouple the task and notification contexts

## API Endpoints

| Method  | Path                        | Description              | Status  |
|---------|-----------------------------|--------------------------|---------|
| `POST`  | `/api/tasks`                | Create a new task        | `201`   |
| `PATCH` | `/api/tasks/{id}/complete`  | Mark a task as completed | `204`   |
| `GET`   | `/api/tasks?assignedTo=...` | List tasks by assignee   | `200`   |

### Create a task

```bash
curl -X POST http://localhost:8082/api/tasks \
  -H "Content-Type: application/json" \
  -d '{"title": "Fix login bug", "description": "Users cannot log in", "assignedTo": "alice"}'
```

**Response** `201 Created`:
```json
{
  "id": "a1b2c3d4-...",
  "title": "Fix login bug",
  "status": "PENDING",
  "assignedTo": "alice"
}
```

### Complete a task

```bash
curl -X PATCH http://localhost:8082/api/tasks/{id}/complete
```

### Get tasks by assignee

```bash
curl http://localhost:8082/api/tasks?assignedTo=alice
```

### Error responses

| Code  | Error Code       | Cause                        |
|-------|------------------|------------------------------|
| `404` | TASK_NOT_FOUND   | Task ID does not exist       |
| `409` | INVALID_STATE    | Task is already completed    |

## Prerequisites

- **Java 25+**
- **PostgreSQL** running on `localhost:5432` with a database named `taskflow`
- **Maven 3.9+** (or use the included wrapper `./mvnw`)

## Configuration

Database credentials are read from environment variables with defaults:

| Variable      | Default    |
|---------------|------------|
| `DB_USER`     | `postgres` |
| `DB_PASSWORD` | `postgres` |

Server runs on **port 8082**. Hibernate auto-updates the schema (`ddl-auto: update`).

## Running

```bash
# Start PostgreSQL, then:
./mvnw spring-boot:run
```

## Testing

Tests use a **FakeTaskRepository** (in-memory HashMap) for pure unit testing without Spring context or database:

```bash
./mvnw test
```

Test coverage:
- `CreateTaskUseCaseTest` — task creation and validation
- `CompleteTaskUseCaseTest` — task completion and event publishing
- `TaskFlowApplicationTests` — Spring context integration test
