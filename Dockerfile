FROM gradle:7.6-jdk17 as build

WORKDIR /app

COPY build.gradle settings.gradle ./

COPY gradle gradle

RUN gradle build --no-daemon

COPY src /app/src

RUN gradle bootJar --no-daemon

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/build/libs/BackendDeveloperTask-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 9090


ENTRYPOINT ["java", "-jar", "app.jar"]
