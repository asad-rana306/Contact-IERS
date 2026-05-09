FROM maven:3.9.6-eclipse-temurin-21-jammy AS builder

WORKDIR /app

# Cache dependencies first to speed up rebuilds.
COPY pom.xml ./
RUN mvn -q -DskipTests dependency:go-offline

COPY src/ ./src/
RUN mvn -q -DskipTests clean package \
    && JAR_FILE="$(find target -maxdepth 1 -type f -name '*.jar' ! -name '*original*' | head -n 1)" \
    && cp "$JAR_FILE" /app/app.jar

FROM eclipse-temurin:21-jre-jammy

WORKDIR /app
COPY --from=builder /app/app.jar ./app.jar

# Run with an unprivileged user in runtime container.
RUN useradd --system --create-home spring
USER spring

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

