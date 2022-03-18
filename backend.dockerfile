FROM gradle:7.4.1-jdk11-alpine AS build

COPY --chown=gradle:gradle . /
WORKDIR /backend
RUN ./gradlew openApiGenerate
RUN ./gradlew build -x test --no-daemon

FROM openjdk:11-jre-slim

EXPOSE 8080
RUN mkdir /app
COPY --from=build /backend/build/libs/*SNAPSHOT.jar /app/application.jar
WORKDIR /app
CMD ["java", "-jar", "-Xms128M",  "-Xmx256M", "application.jar"]