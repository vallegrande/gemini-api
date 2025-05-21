# Gemini Spring Boot

## Descripción

Gemini Spring Boot es una aplicación web desarrollada con Spring Boot que integra la API de Gemini de Google para generar contenido mediante inteligencia artificial. La aplicación ofrece una interfaz de usuario intuitiva que permite realizar consultas a la API de Gemini y utilizar un servicio de traducción integrado.

## Características Principales

### Consultas a Gemini API
- Interfaz de chat para realizar consultas a la API de Gemini
- Historial de consultas almacenado en MongoDB
- Visualización de respuestas en formato Markdown
- Posibilidad de eliminar elementos del historial

### Servicio de Traducción
- Traducción de textos entre múltiples idiomas
- Soporte para español, inglés, francés, alemán, italiano y portugués
- Historial de traducciones almacenado en MongoDB
- Opción para limpiar el historial de traducciones

## Tecnologías Utilizadas

- **Backend**: Spring Boot, WebFlux (programación reactiva)
- **Frontend**: HTML, CSS, JavaScript
- **Base de Datos**: MongoDB
- **APIs Externas**: Google Gemini API
- **Dependencias**: Spring Reactive Web, Spring Data MongoDB Reactive

## Requisitos

- Java 17 o superior
- MongoDB
- Conexión a Internet (para acceder a la API de Gemini)
- API Key de Google Gemini

## Instalación y Configuración

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/tu-usuario/GeminiSpringBootV1.git
   cd GeminiSpringBootV1/gemini
   ```

2. Configurar la API Key de Gemini en el archivo `application.yml` o mediante variables de entorno.

3. Asegurarse de tener MongoDB en ejecución.

4. Compilar y ejecutar la aplicación:
   ```bash
   ./gradlew bootRun
   ```

5. Acceder a la aplicación en el navegador:
   ```
   http://localhost:8080
   ```

## Estructura del Proyecto

```
gemini/
├── src/
│   ├── main/
│   │   ├── java/vallegrande/gemini/
│   │   │   ├── controller/
│   │   │   │   ├── GeminiController.java
│   │   │   │   └── TranslationController.java
│   │   │   ├── model/
│   │   │   │   ├── ApiTest.java
│   │   │   │   ├── GeminiRequest.java
│   │   │   │   ├── Translation.java
│   │   │   │   ├── TranslationRequest.java
│   │   │   │   └── TranslationResponse.java
│   │   │   ├── repository/
│   │   │   │   ├── ApiTestRepository.java
│   │   │   │   └── TranslationRepository.java
│   │   │   ├── service/
│   │   │   │   ├── GeminiService.java
│   │   │   │   └── TranslationService.java
│   │   │   └── GeminiApplication.java
│   │   └── resources/
│   │       ├── static/
│   │       │   ├── css/
│   │       │   │   ├── styles.css
│   │       │   │   └── translation-history.css
│   │       │   └── index.html
│   │       └── application.yml
│   └── test/
└── build.gradle
```

## Uso de la Aplicación

### Consultas a Gemini
1. Escribe tu consulta en el campo de texto en la parte inferior de la interfaz.
2. Presiona el botón de enviar o la tecla Enter.
3. La respuesta de Gemini se mostrará en el área de chat.
4. El historial de consultas se guarda automáticamente y se muestra en el panel lateral.

### Traducción de Textos
1. Escribe el texto que deseas traducir en el área de texto del panel de traducción.
2. Selecciona el idioma de destino en el menú desplegable.
3. Haz clic en el botón "Traducir".
4. La traducción se mostrará en el área de resultado.

## Interfaz de Usuario

La aplicación cuenta con una interfaz moderna y responsiva dividida en tres secciones principales:

- **Panel de Chat**: Muestra las consultas del usuario y las respuestas de Gemini.
- **Panel de Historial**: Muestra el historial de consultas realizadas.
- **Panel de Traducción**: Permite traducir textos entre diferentes idiomas.

En dispositivos móviles, la interfaz se adapta para mostrar las secciones de manera vertical.

