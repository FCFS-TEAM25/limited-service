FROM eclipse-temurin:17-jdk as jar_builder

COPY . .

ARG USERNAME=${USERNAME}
ARG SECRET_KEY=${SECRET_KEY}

RUN ./gradlew clean bootJar -Pgpr.user=${USERNAME} -Pgpr.key=${SECRET_KEY}

FROM eclipse-temurin:17-jre

COPY --from=jar_builder /build/libs/*jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]