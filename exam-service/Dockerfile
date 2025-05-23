FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . ./exam-service
WORKDIR /app/exam-service
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/exam-service/target/*.jar exam-service.jar
EXPOSE 9002
ENTRYPOINT ["java", "-jar", "exam-service.jar"]