FROM openjdk:17-jre-slim

WORKDIR /app

COPY target/content-service-1.0-SNAPSHOT.jar app.jar

EXPOSE 8083

ENTRYPOINT ["java", "-jar", "app.jar"]