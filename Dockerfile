# Usa una imagen base con OpenJDK
FROM eclipse-temurin:21-jdk

WORKDIR /app

# Copia el archivo JAR de tu aplicación al contenedor
COPY target/*.jar app.jar

# Exponemos el puerto 8080
EXPOSE 8080

# Ejecutamos la aplicación
#ENTRYPOINT ["java", "-jar", "/app/app.jar"]

ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005", "-jar", "/app/app.jar"]
