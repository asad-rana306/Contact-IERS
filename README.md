# IERS Profile and Contacts Service

**Project name:** asad

This Spring Boot microservice manages emergency contacts and user medical profiles for the Intelligent Emergency Response System (IERS).

## Tech Stack
- Java 21
- Spring Boot
- Spring Data JPA
- Spring Security
- PostgreSQL
- Lombok

## Features
- CRUD for emergency contacts
- Verify a contact
- Update contact priority
- Admin contact statistics
- Create and update user medical profiles by `userName`
- Role-protected admin endpoints
- Structured logging with `slf4j`
- DTO-based request/response payloads
- Centralized JSON error handling

## Run Locally
1. Make sure PostgreSQL is running.
2. Create a database named `iers_contacts_db`.
3. Update `src/main/resources/application.yaml` if your DB credentials differ.
4. Start the app:

```bash
bash mvnw spring-boot:run
```

## Default Security Users
The project includes in-memory users for quick testing:
- `admin / admin123` with role `ADMIN`
- `user / user123` with role `USER`

## API Payloads
- `ContactRequestDto` / `ContactResponseDto`
- `ContactPriorityRequestDto`
- `UserProfileRequestDto` / `UserProfileResponseDto`
- `ApiErrorResponseDto`

## Notes
- Profile endpoints use `userName` in the path: `/api/profiles/user/{userName}`.
- Test execution uses an in-memory H2 database, so it does not need PostgreSQL.
- The production configuration uses JPA `ddl-auto=update` for PostgreSQL.

