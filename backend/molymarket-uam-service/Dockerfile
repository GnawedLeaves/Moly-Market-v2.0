# Stage 1: Build the application
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app

# Copy gradle wrapper and configuration
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# Fix line endings and ensure gradlew is executable
RUN sed -i 's/\r$//' gradlew && chmod +x gradlew

# Download dependencies (this step will be cached)
RUN ./gradlew dependencies --no-daemon

# Copy source code and build the application
COPY src src
RUN ./gradlew build -x test --no-daemon

# Stage 2: Runtime image
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Create a non-root user for security
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copy the built JAR from the build stage
# Note: Using wildcard to match the artifact name
COPY --from=build /app/build/libs/molymarket-uam-service-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port (8082 as configured in application.yml)
EXPOSE 8082

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
