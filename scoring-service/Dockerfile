FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . ./scoring-service
WORKDIR /app/scoring-service
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/scoring-service/target/*.jar scoring-service.jar
EXPOSE 9003
ENTRYPOINT ["java", "-jar", "scoring-service.jar"]