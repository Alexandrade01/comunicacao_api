FROM maven:3.9.11-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn -q -DskipTests package

FROM amazoncorretto:17-alpine
WORKDIR /app

COPY --from=build /app/target/*SNAPSHOT.jar /app/app.jar

EXPOSE 8090

ENTRYPOINT ["java", "-jar", "app.jar"]
