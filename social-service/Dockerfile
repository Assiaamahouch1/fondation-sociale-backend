FROM openjdk:17-jre-slim

WORKDIR /app

COPY target/social-service-1.0-SNAPSHOT.jar app.jar

EXPOSE 8084

ENTRYPOINT ["java", "-jar", "app.jar"]