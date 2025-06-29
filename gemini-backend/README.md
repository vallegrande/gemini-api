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
   mvn spring-boot:run
   ```

5. Acceder a la aplicación en el navegador:
   ```
   http://localhost:8080
   ```

## Estructura del Proyecto

```
gemini/
├── .git/
├── .gitattributes
├── .github/
│   └── workflows/
│       └── dockerhub.yml
├── .gitignore
├── Dockerfile
├── pom.xml
├── README.md
└── src/
    ├── main/
    │   ├── java/
    │   │   └── vallegrande/
    │   │       └── gemini/
    │   │           ├── GeminiApplication.java
    │   │           ├── controller/
    │   │           │   ├── GeminiController.java
    │   │           │   └── TranslationController.java
    │   │           ├── model/
    │   │           │   ├── ApiTest.java
    │   │           │   ├── GeminiRequest.java
    │   │           │   ├── Translation.java
    │   │           │   ├── TranslationRequest.java
    │   │           │   └── TranslationResponse.java
    │   │           ├── repository/
    │   │           │   ├── ApiTestRepository.java
    │   │           │   └── TranslationRepository.java
    │   │           └── service/
    │   │               ├── GeminiService.java
    │   │               └── TranslationService.java
    │   └── resources/
    │       └── application.yml
    └── test/
        └── java/
            └── vallegrande/
                └── gemini/
                    └── GeminiApplicationTests.java
```

## API Endpoints

This section details the available API endpoints for interacting with the Gemini and Translation services.

### Gemini API (`/api/gemini`)

-   **`POST /generate`**
    -   **Description:** Generates content using the Gemini model based on a given prompt.
    -   **Request Body:**
        ```json
        {
          "text": "Your prompt goes here"
        }
        ```
    -   **Response Body:**
        ```json
        "Generated text response from Gemini"
        ```

-   **`GET /history`**
    -   **Description:** Retrieves the history of Gemini interactions that have not been logically deleted.
    -   **Response Body:**
        ```json
        [
          {
            "id": "string",
            "prompt": "string",
            "response": "string",
            "timestamp": "string",
            "deleted": false
          }
        ]
        ```

-   **`PUT /history/{id}`**
    -   **Description:** Updates a specific Gemini history item by its ID.
    -   **Request Body:**
        ```json
        {
          "prompt": "Updated prompt string",
          "response": "Updated response string"
        }
        ```
    -   **Response Body:**
        ```json
        {
          "id": "string",
          "prompt": "Updated prompt string",
          "response": "Updated response string",
          "timestamp": "string",
          "deleted": false
        }
        ```

-   **`DELETE /history/{id}`**
    -   **Description:** Logically deletes a Gemini history item by its ID. The item will be marked as deleted but remains in the database.
    -   **Response Body:**
        ```json
        {
          "id": "string",
          "prompt": "string",
          "response": "string",
          "timestamp": "string",
          "deleted": true
        }
        ```

-   **`POST /history/{id}/restore`**
    -   **Description:** Restores a logically deleted Gemini history item by its ID.
    -   **Response Body:**
        ```json
        {
          "id": "string",
          "prompt": "string",
          "response": "string",
          "timestamp": "string",
          "deleted": false
        }
        ```

-   **`DELETE /history/permanent/{id}`**
    -   **Description:** Permanently deletes a Gemini history item from the database by its ID.
    -   **Response Body:** `(No content)`

-   **`GET /history/all`**
    -   **Description:** Retrieves all Gemini history items, including those that have been logically deleted.
    -   **Response Body:**
        ```json
        [
          {
            "id": "string",

-   **`PUT /edit/{id}`**
    -   **Description:** Edits the prompt of a specific Gemini history item by its ID.
    -   **Request Body:**
        ```json
        {
          "text": "Your updated prompt goes here"
        }
        ```
    -   **Response Body (Success):**
        ```json
        "Content with ID {id} updated successfully."
        ```
    -   **Response Body (Not Found):**
        ```json
        "Content with ID {id} not found."
        ```

-   **`GET /history/all`**
    -   **Description:** Retrieves all Gemini history items, including those that have been logically deleted.
    -   **Response Body:**
        ```json
        [
          {
            "id": "string",
            "prompt": "string",
            "response": "string",
            "timestamp": "string",
            "deleted": true/false
          }
        ]
        ```

### Translation API (`/api/translation`)

-   **`POST /translate`**
    -   **Description:** Translates text from a source language to a target language.
    -   **Request Body:**
        ```json
        {
          "text": "Text to translate",
          "targetLanguage": "es",
          "sourceLanguage": "en" // Optional
        }
        ```
    -   **Response Body:**
        ```json
        "Translated text string"
        ```

-   **`GET /history`**
    -   **Description:** Retrieves the history of translation requests that have not been logically deleted.
    -   **Response Body:**
        ```json
        [
          {
            "id": "string",
            "textToTranslate": "string",
            "translatedText": "string",
            "sourceLanguage": "string",
            "targetLanguage": "string",
            "timestamp": "string",
            "deleted": false
          }
        ]
        ```

-   **`PUT /history/{id}`**
    -   **Description:** Updates a specific translation history item by its ID.
    -   **Request Body:**
        ```json
        {
          "textToTranslate": "Updated text",
          "translatedText": "Updated translated text",
          "sourceLanguage": "en",
          "targetLanguage": "es"
        }
        ```
    -   **Response Body:**
        ```json
        {
           "id": "string",
           "textToTranslate": "Updated text",
           "translatedText": "Updated translated text",
           "sourceLanguage": "en",
           "targetLanguage": "es",
           "timestamp": "string",
           "deleted": false
        }
        ```

-   **`DELETE /history/{id}`**
    -   **Description:** Logically deletes a translation history item by its ID.
    -   **Response Body:**
        ```json
        {
           "id": "string",
           "textToTranslate": "string",
           "translatedText": "string",
           "sourceLanguage": "string",
           "targetLanguage": "string",
           "timestamp": "string",
           "deleted": true
        }
        ```

-   **`POST /history/{id}/restore`**
    -   **Description:** Restores a logically deleted translation history item by its ID.
    -   **Response Body:**
        ```json
        {
           "id": "string",
           "textToTranslate": "string",
           "translatedText": "string",
           "sourceLanguage": "string",
           "targetLanguage": "string",
           "timestamp": "string",
           "deleted": false
        }
        ```

-   **`DELETE /clear`**
    -   **Description:** Permanently deletes all translation history items from the database.
    -   **Response Body:** `(No content)`

-   **`GET /history/all`**
    -   **Description:** Retrieves all translation history items, including those that have been logically deleted.
    -   **Response Body:**
        ```json
        [
          {
            "id": "string",
            "textToTranslate": "string",
            "translatedText": "string",
            "sourceLanguage": "string",
            "targetLanguage": "string",
            "timestamp": "string",
            "deleted": true/false
          }
        ]
        ```
