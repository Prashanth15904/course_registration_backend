# ===============================
# Build Stage
# ===============================
FROM maven:3.9.9-eclipse-temurin-17 AS builder

WORKDIR /app

# Copy Maven project files
COPY pom.xml .

# Copy source code
COPY src ./src

# Build Spring Boot application
RUN mvn clean package -DskipTests


# ===============================
# Runtime Stage
# ===============================
FROM eclipse-temurin:17-jre-focal

WORKDIR /app

# Copy generated JAR
COPY --from=builder /app/target/*.jar app.jar

# Application port
EXPOSE 8084

# Start Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
