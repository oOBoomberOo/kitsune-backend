FROM maven:3-amazoncorretto-21 AS build

WORKDIR /app

COPY pom.xml /app
COPY src/main/java/com/kitsune/backend/KitsuneBackendApplication.java /app/src/main/java/com/kitsune/backend/KitsuneBackendApplication.java

RUN mvn clean install

COPY . /app

RUN FLYWAY_ENABLED=false && mvn package

EXPOSE 8080

# run spring boot
ENTRYPOINT ["mvn", "spring-boot:run"]
