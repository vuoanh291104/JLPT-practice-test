FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . ./question-bank-service
WORKDIR /app/question-bank-service
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/question-bank-service/target/*.jar question-bank-service.jar
EXPOSE 9001
ENTRYPOINT ["java", "-jar", "question-bank-service.jar"]