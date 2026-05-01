FROM eclipse-temurin:11-jre-jammy
WORKDIR /app
COPY target/achat-1.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
