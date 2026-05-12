FROM eclipse-temurin:21.0.9_10-jdk-jammy AS build
WORKDIR /app

COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .

COPY order-service/build.gradle.kts order-service/
COPY order/build.gradle.kts order/

RUN ./gradlew dependencies --no-daemon

COPY . .

RUN ./gradlew :order-service:bootJar -x test --no-daemon

FROM eclipse-temurin:21.0.9_10-jre-jammy
WORKDIR /app

RUN useradd -ms /bin/bash springuser
USER springuser

COPY --from=build /app/order-service/build/libs/order-service.jar app.jar

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=staging", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "app.jar"]

EXPOSE 8080