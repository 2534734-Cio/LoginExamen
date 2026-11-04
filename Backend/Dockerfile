FROM eclipse-temurin:25-jdk AS build
COPY --from=eclipse-temurin:21-jdk /opt/java/openjdk /opt/jdk21
WORKDIR /app
COPY . .
RUN chmod +x gradlew && \
    ./gradlew installDist --no-daemon -Porg.gradle.java.installations.paths=/opt/jdk21

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/build/install/backend /app

ENV JAVA_OPTS="-Xmx300m"
EXPOSE 8080
CMD ["/app/bin/backend"]
