# ---------- Etapa 1: compilar el backend ----------
# Gradle de tu proyecto pide JDK 25 para correr y JDK 21 para compilar (jvmToolchain(21)),
# así que usamos una imagen con JDK 25 y le copiamos también el JDK 21.
FROM eclipse-temurin:25-jdk AS build
COPY --from=eclipse-temurin:21-jdk /opt/java/openjdk /opt/jdk21
WORKDIR /app
COPY . .
RUN chmod +x gradlew && \
    ./gradlew installDist --no-daemon -Porg.gradle.java.installations.paths=/opt/jdk21

# ---------- Etapa 2: ejecutar el backend ----------
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/build/install/backend /app
# Límite de memoria para que quepa en el plan gratis de Render (512 MB)
ENV JAVA_OPTS="-Xmx300m"
EXPOSE 8080
CMD ["/app/bin/backend"]
