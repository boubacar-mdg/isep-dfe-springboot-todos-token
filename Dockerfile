FROM openjdk:17-jdk-slim AS build

COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN ./mvnw dependency:resolve

COPY src src
RUN ./mvnw package

FROM amazoncorretto:17
COPY --from=build target/*.jar app.jar  

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]