FROM openjdk:22-jdk-slim
WORKDIR /app
COPY target/gateway-app-0.0.1-SNAPSHOT.jar gateway-service.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "gateway-service.jar"]