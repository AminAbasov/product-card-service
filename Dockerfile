# Step 1: Build stage
FROM gradle:7.6-jdk17 as build

# Set working directory in container
WORKDIR /app

# Copy build.gradle and settings.gradle
COPY build.gradle settings.gradle ./

# Copy Gradle wrapper
COPY gradle gradle

# Download dependencies (this will cache dependencies if no changes to build.gradle)
RUN gradle build --no-daemon

# Copy the rest of the source code
COPY src /app/src

# Build the project and package the jar file
RUN gradle bootJar --no-daemon

# Step 2: Run stage
FROM openjdk:17-jdk-slim

# Set working directory in container
WORKDIR /app

# Copy the jar file from the build stage
COPY --from=build /app/build/libs/BackendDeveloperTask-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the app will run on
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
