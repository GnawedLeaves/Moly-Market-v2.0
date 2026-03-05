# Stage 1: Build the application
FROM gradle:9.3.1-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
# Build the application, skipping tests to speed up the process
RUN sed -i 's/\r$//' ./gradlew && chmod +x ./gradlew && ./gradlew build --no-daemon -x test
# Rename the JAR to a standard name for easier copying
RUN cp build/libs/$(ls build/libs/ | grep -v "plain" | head -n 1) build/libs/app.jar

# Stage 2: Create the runtime image
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Port 8083 is configured in application.yml
EXPOSE 8083

# Copy the built jar from the build stage
COPY --from=build /home/gradle/src/build/libs/app.jar /app/molymarket-payment-service.jar

# Run the application
ENTRYPOINT ["java", "-jar", "/app/molymarket-payment-service.jar"]
