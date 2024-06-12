FROM maven:3 AS build

WORKDIR /app

COPY pom.xml /app
COPY src/main/java/com/kitsune/backend/KitsuneBackendApplication.java /app/src/main/java/com/kitsune/backend/KitsuneBackendApplication.java

RUN mvn clean install

COPY . /app

RUN FLYWAY_ENABLED=false && mvn package

FROM amazoncorretto:21

EXPOSE 8080

WORKDIR /app
COPY --from=build /app/target/*.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
