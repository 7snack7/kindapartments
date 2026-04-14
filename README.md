# Task Service

## Stack

- Kotlin
- Spring Boot
- WebFlux
- Reactor
- JdbcClient
- PostgreSQL
- Flyway

## Run

./gradlew bootRun

## API

| **Method**  | **Path**                             |
|-------------|--------------------------------------|
| **POST**    | /api/tasks                           |
| **GET**     | /api/tasks?page=0&size=10&status=NEW |
| **GET**     | /api/tasks/{id}                      |
| **PATCH**   | /api/tasks/{id}/status               |
| **DELETE**  | /api/tasks/{id}                      |