# Stage 1: Build
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app

# Copy gradle files
COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./

# Grant execution permissions for the gradlew script
RUN sed -i 's/\r$//' gradlew && chmod +x gradlew

# Download dependencies (this layer is cached if build files don't change)
RUN ./gradlew dependencies --no-daemon

# Copy source code and build the application
COPY src src
RUN ./gradlew build -x test --no-daemon

# Stage 2: Runtime
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Create a non-root user for security
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copy the built JAR from the build stage
COPY --from=build /app/build/libs/molymarket-product-order-service-0.0.1-SNAPSHOT.jar app.jar

# Application environment variables (overridable)
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/molymarket
ENV SPRING_DATASOURCE_USERNAME=postgres
ENV SPRING_DATASOURCE_PASSWORD=polymoly123
ENV AUTH_SERVICE_URL=http://molymarket-uam-service:8082
ENV PAYMENT_SERVICE_URL=http://molymarket-payment-service:8083
ENV NOTIFICATION_SERVICE_URL=http://molymarket-notification-service:8084

EXPOSE 8085

ENTRYPOINT ["java", "-jar", "app.jar"]
