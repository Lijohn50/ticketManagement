# Build stage
FROM maven:3.9-eclipse-temurin-25 AS builder

WORKDIR /app

# Copy Maven configuration first for better Docker layer caching
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the Spring Boot application
RUN mvn clean package -DskipTests


# Runtime stage
FROM eclipse-temurin:25-jre

WORKDIR /app

# Copy the generated Spring Boot jar
COPY --from=builder /app/target/*.jar app.jar

# Render uses its PORT environment variable.
# 10000 is Render's default web-service port.
EXPOSE 10000

# Start Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]
