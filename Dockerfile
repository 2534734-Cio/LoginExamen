FROM gradle:8.5-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src/Backend
RUN gradle installDist --no-daemon

FROM eclipse-temurin:17-jre-alpine
EXPOSE 8080
RUN mkdir /app
COPY --from=build /home/gradle/src/Backend/build/install/ /app/
WORKDIR /app
CMD ["sh", "-c", "./*/bin/*"]