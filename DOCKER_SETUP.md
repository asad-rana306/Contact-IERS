# Docker Setup Guide for Contacts Application

This guide provides instructions for building, running, and deploying the Contacts application using Docker.

## Prerequisites

- Docker (version 20.10 or later)
- Docker Compose (version 2.0 or later)

## Project Structure

```
.
├── Dockerfile               # Multi-stage Docker build configuration
├── docker-compose.yaml      # Docker Compose configuration
├── .dockerignore           # Files to exclude from Docker build context
├── pom.xml                 # Maven project configuration
└── src/                    # Application source code
```

## Building and Running with Docker Compose (Recommended)

The easiest way to get started is to use Docker Compose, which handles both the application and PostgreSQL database.

### Build the Docker Image

```bash
docker-compose build
```

### Start the Services

```bash
docker-compose up -d
```

This command will:
- Build the Docker image if it doesn't exist
- Start the PostgreSQL database
- Start the Spring Boot application
- Create a network for inter-service communication

### View Logs

```bash
# View all logs
docker-compose logs -f

# View application logs only
docker-compose logs -f contacts_app

# View database logs only
docker-compose logs -f postgres
```

### Stop the Services

```bash
docker-compose down
```

To also remove the database volume:

```bash
docker-compose down -v
```

## Building and Running Individual Docker Container

If you prefer to build and run the Docker image manually:

### Build the Image

```bash
docker build -t contacts-app:latest .
```

### Run the Container

```bash
docker run -d \
  --name contacts_app \
  -p 8081:8081 \
  -e POSTGRES_USERNAME=postgres \
  -e POSTGRES_PASSWORD=password \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/iers_contacts_db \
  contacts-app:latest
```

## Pushing to Docker Registry

### Tag the Image

```bash
# For Docker Hub
docker tag contacts-app:latest your-username/contacts-app:latest

# For other registries
docker tag contacts-app:latest your-registry.com/your-username/contacts-app:latest
```

### Push the Image

```bash
# For Docker Hub (login first: docker login)
docker push your-username/contacts-app:latest

# For other registries
docker push your-registry.com/your-username/contacts-app:latest
```

## Configuration

### Environment Variables

The application supports the following environment variables:

- `POSTGRES_USERNAME` - PostgreSQL username (default: postgres)
- `POSTGRES_PASSWORD` - PostgreSQL password (default: password)
- `SPRING_DATASOURCE_URL` - Database connection URL

### Database Configuration

The application connects to PostgreSQL on port 5432:
- Default Username: `postgres`
- Default Password: `password`
- Database Name: `iers_contacts_db`

### Application Port

The Spring Boot application runs on port 8081.

## Accessing the Application

Once the containers are running, access the application at:

```
http://localhost:8081
```

## Troubleshooting

### Container fails to start

Check the logs:
```bash
docker-compose logs contacts_app
```

### Database connection error

Ensure:
1. PostgreSQL container is healthy: `docker-compose ps`
2. Environment variables are correctly set
3. The `postgres` service is fully initialized (check health status)

### Port conflicts

If ports 5432 or 8081 are already in use, modify the `docker-compose.yaml`:

```yaml
ports:
  - "5433:5432"  # Changed from 5432
  - "8082:8081"  # Changed from 8081
```

### Remove all containers and volumes

```bash
docker-compose down -v
```

## Docker Best Practices Used

1. **Multi-stage Build**: Keeps the final image lean by using a build stage for compilation
2. **Alpine Base Images**: PostgreSQL uses Alpine Linux for a smaller footprint
3. **Health Checks**: Both services include health checks
4. **Volumes**: PostgreSQL data is persisted using Docker volumes
5. **Networks**: Services communicate through a dedicated bridge network
6. **.dockerignore**: Reduces build context size
7. **Environment Variables**: Configuration through environment variables instead of hardcoding

## Additional Resources

- [Docker Documentation](https://docs.docker.com/)
- [Docker Compose Documentation](https://docs.docker.com/compose/)
- [Spring Boot Docker Guide](https://spring.io/guides/gs/spring-boot-docker/)

