FROM openjdk:17-jdk-slim

# Nombre exacto del archivo JAR (verifica en tu carpeta target después de hacer mvn package)
ARG JAR_FILE=target/JWTImplementation-0.0.1-SNAPSHOT.jar

# Copia el JAR al contenedor con un nombre simplificado
COPY ${JAR_FILE} app_spa.jar

# debe coincidir con server.port
EXPOSE 8080

# Comando de ejecución
ENTRYPOINT ["java", "-jar", "app_spa.jar"]