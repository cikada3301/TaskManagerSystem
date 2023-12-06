# Build stage
#
FROM maven:3.8.3-openjdk-17 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package
EXPOSE 8081
ENTRYPOINT ["java","-jar","/target/TaskManagerSystemAPI-0.0.1-SNAPSHOT.jar app.jar"]