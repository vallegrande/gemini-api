FROM eclipse-temurin:17-jdk as build
WORKDIR /workspace/app

# Copiar archivos de gradle
COPY gradle gradle
COPY gradlew gradlew.bat ./
COPY build.gradle settings.gradle ./

# Descargar dependencias
RUN ./gradlew dependencies --no-daemon

# Copiar el código fuente
COPY . .

# Construir la aplicación
RUN ./gradlew build -x test --no-daemon

# Etapa de ejecución
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copiar el JAR construido
COPY --from=build /workspace/app/build/libs/*.jar app.jar

# Variables de entorno para MongoDB (pueden ser sobrescritas al ejecutar el contenedor)
ENV SPRING_DATA_MONGODB_URI=mongodb+srv://cristopherguzman:orTIWL10NmFAo3S5@cluster0.o8sc9.mongodb.net/gemini?retryWrites=true&w=majority&appName=Cluster0
ENV SPRING_DATA_MONGODB_DATABASE=gemini

# Puerto que expone la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app/app.jar"]