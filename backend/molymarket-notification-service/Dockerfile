# Stage 1: Build the application
FROM gradle:9.3.1-jdk17-alpine AS build
WORKDIR /app

# Copy gradle files for caching
COPY build.gradle settings.gradle ./
COPY gradlew ./
COPY gradle ./gradle

# Fix line endings and ensure gradlew is executable
RUN sed -i 's/\r$//' gradlew && chmod +x gradlew

# Download dependencies (cache layer)
RUN ./gradlew dependencies --no-daemon || true

# Copy source code
COPY src ./src

# Build the application
RUN ./gradlew clean bootJar --no-daemon

# Stage 2: Run the application
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /app/build/libs/molymarket-notification-service-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port
EXPOSE 8084

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
