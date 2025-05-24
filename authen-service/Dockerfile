FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . ./authen-service
WORKDIR /app/authen-service
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/authen-service/target/*.jar authen-service.jar
EXPOSE 9004
ENTRYPOINT ["java", "-jar", "authen-service.jar"]