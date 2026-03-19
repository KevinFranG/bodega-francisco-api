FROM eclipse-temurin:21-jdk
LABEL authors="KevinCyndaquil"

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar"]