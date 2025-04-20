FROM eclipse-temurin:17-jdk

COPY ./build/libs/limited-service-0.0.1-SNAPSHOT.jar limited-service.jar

ENTRYPOINT ["java", "-jar", "limited-service.jar"]