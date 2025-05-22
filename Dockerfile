# Etapa de compilación
FROM eclipse-temurin:17-jdk AS build
WORKDIR /workspace/app

# Copiar archivos necesarios para Gradle
COPY gradle gradle
COPY gradlew gradlew.bat ./

# Asegurar que gradlew tiene permisos de ejecución
RUN sed -i 's/\r$//' gradlew && chmod +x gradlew

# Copiar archivos de configuración de Gradle
COPY build.gradle settings.gradle ./

# Descargar dependencias
RUN ./gradlew dependencies --no-daemon

# Copiar el resto del código fuente
COPY . .

# Asegurar que todos los scripts shell tengan permisos de ejecución
RUN find . -name "*.sh" -type f -exec sed -i 's/\r$//' {} \; -exec chmod +x {} \;

# Asegurar que gradlew tiene permisos de ejecución
RUN chmod +x ./gradlew

# Construir la aplicación
RUN ./gradlew build -x test --no-daemon

# Etapa de ejecución
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copiar el JAR desde la etapa de construcción
COPY --from=build /workspace/app/build/libs/*.jar app.jar

# Variables de entorno
ENV SPRING_DATA_MONGODB_URI=mongodb+srv://cristopherguzman:orTIWL10NmFAo3S5@cluster0.o8sc9.mongodb.net/gemini?retryWrites=true&w=majority&appName=Cluster0
ENV SPRING_DATA_MONGODB_DATABASE=gemini

# Exponer el puerto
EXPOSE 8080

# Ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
