#FROM maven:3.9.8-eclipse-temurin-17 as build
#
#COPY src /usr/src/app/src
#COPY pom.xml /usr/src/app
#
#RUN mvn -f /usr/src/app/pom.xml clean package -DskipTests
#
#FROM openjdk:21-ea-30-jdk-oracle
#
#COPY --from=build /usr/src/app/target/*.jar app.jar
#
#EXPOSE 8080
#
#ENTRYPOINT ["java", "-jar", "app.jar"]

#Tentando fazer por mim mesmo

FROM maven:3.9.8-eclipse-temurin-17 as build

WORKDIR /app

COPY src ./src
COPY pom.xml .

RUN mvn -f pom.xml clean package

FROM eclipse-temurin:17.0.10_7-jre-alpine

COPY --from=build /app/target/*.jar myapp.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "myapp.jar"]
