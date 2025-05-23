FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . ./api-gateway
WORKDIR /app/api-gateway
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/api-gateway/target/*.jar api-gateway.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "api-gateway.jar"]