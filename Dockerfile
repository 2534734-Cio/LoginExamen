FROM gradle:9.5.1-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src/Backend
RUN chmod +x gradlew && ./gradlew installDist --no-daemon

FROM eclipse-temurin:17-jre-alpine
EXPOSE 8080
RUN mkdir /app
COPY --from=build /home/gradle/src/Backend/build/install/ /app/
WORKDIR /app
CMD ["sh", "-c", "$(ls /app/*/bin | head -1)"]