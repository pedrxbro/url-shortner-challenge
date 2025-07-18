FROM openjdk:17-jdk-slim
WORKDIR /url-shortner
COPY target/url-shortner-0.0.1-SNAPSHOT.jar backend.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "backend.jar"]
