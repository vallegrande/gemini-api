package vallegrande.gemini.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import vallegrande.gemini.model.ApiTest;
import vallegrande.gemini.repository.ApiTestRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GeminiService {

    private static final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent";
    private static final String API_KEY = "AIzaSyBPvEaC_Em6lJYhuudjBlBi1jaoE4hXJ0g";
    
    private final WebClient webClient;
    private final ApiTestRepository apiTestRepository;
    
    @Autowired
    public GeminiService(ApiTestRepository apiTestRepository) {
        this.webClient = WebClient.builder().baseUrl(API_URL).build();
        this.apiTestRepository = apiTestRepository;
    }
    
    public Mono<String> generateContent(String prompt) {
        // Preparar el cuerpo de la solicitud según la estructura de la API de Gemini
        Map<String, Object> requestBody = new HashMap<>();
        Map<String, Object> contents = new HashMap<>();
        Map<String, Object> parts = new HashMap<>();
        
        parts.put("text", prompt);
        
        List<Map<String, Object>> partsList = new ArrayList<>();
        partsList.add(parts);
        
        contents.put("parts", partsList);
        
        List<Map<String, Object>> contentsList = new ArrayList<>();
        contentsList.add(contents);
        
        requestBody.put("contents", contentsList);
        
        // Realizar la solicitud POST a la API de Gemini usando WebClient
        return webClient
            .post()
            .uri(uriBuilder -> uriBuilder.queryParam("key", API_KEY).build())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(requestBody)
            .retrieve()
            .bodyToMono(Map.class)
            .map(this::extractTextFromResponse)
            .flatMap(generatedText -> saveToHistory(prompt, generatedText).thenReturn(generatedText))
            .onErrorResume(e -> {
                e.printStackTrace();
                return Mono.just("Error al generar contenido: " + e.getMessage());
            });
    }
    
    private String extractTextFromResponse(Map<String, Object> response) {
        try {
            if (response != null && response.containsKey("candidates")) {
                List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.get("candidates");
                if (!candidates.isEmpty()) {
                    Map<String, Object> candidate = candidates.get(0);
                    if (candidate.containsKey("content")) {
                        Map<String, Object> content = (Map<String, Object>) candidate.get("content");
                        if (content.containsKey("parts")) {
                            List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");
                            if (!parts.isEmpty() && parts.get(0).containsKey("text")) {
                                return (String) parts.get(0).get("text");
                            }
                        }
                    }
                }
            }
            return "No se pudo extraer texto de la respuesta";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al procesar la respuesta: " + e.getMessage();
        }
    }
    
    private Mono<ApiTest> saveToHistory(String prompt, String response) {
        ApiTest apiTest = new ApiTest(prompt, response);
        return apiTestRepository.save(apiTest);
    }
    
    public Flux<ApiTest> getHistory() {
        return apiTestRepository.findByDeletedFalse();
    }

    public Flux<ApiTest> getAllHistoryIncludingDeleted() {
        return apiTestRepository.findAll();
    }
    
    public Mono<ApiTest> updateHistoryItem(String id, ApiTest updatedItem) {
        return apiTestRepository.findById(id)
                .flatMap(existingItem -> {
                    existingItem.setPrompt(updatedItem.getPrompt());
                    existingItem.setResponse(updatedItem.getResponse());
                    return apiTestRepository.save(existingItem);
                });
    }

    public Mono<ApiTest> logicalDeleteHistoryItem(String id) {
        return apiTestRepository.findById(id)
                .flatMap(item -> {
                    item.setDeleted(true);
                    return apiTestRepository.save(item);
                });
    }

    public Mono<ApiTest> restoreHistoryItem(String id) {
        return apiTestRepository.findById(id)
                .flatMap(item -> {
                    item.setDeleted(false);
                    return apiTestRepository.save(item);
                });
    }

    public Mono<Void> deleteHistoryItem(String id) {
        return apiTestRepository.deleteById(id);
    }

    public Mono<String> editContent(String id, String newPrompt) {
        return apiTestRepository.findById(id)
                .flatMap(existingItem -> {
                    existingItem.setPrompt(newPrompt);
                    // Optionally, regenerate response based on new prompt or clear it
                    // For now, let's just update the prompt
                    return apiTestRepository.save(existingItem);
                })
                .map(updatedItem -> "Content with ID " + updatedItem.getId() + " updated successfully.")
                .defaultIfEmpty("Content with ID " + id + " not found.");
    }
}