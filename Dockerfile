FROM maven:3.8.3-openjdk-17 AS builder
COPY pom.xml /app/
COPY src /app/src
RUN --mount=type=cache,target=/root/.m2 mvn -f /app/pom.xml clean package -DskipTests

FROM openjdk:17
COPY --from=builder /app/target/TaskManagerSystemAPI-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]