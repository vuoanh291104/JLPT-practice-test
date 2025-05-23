FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . ./discoverserver
WORKDIR /app/discoverserver
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/discoverserver/target/*.jar discoverserver.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "discoverserver.jar"]