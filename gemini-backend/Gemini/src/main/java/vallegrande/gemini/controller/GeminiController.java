package vallegrande.gemini.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import vallegrande.gemini.model.ApiTest;
import vallegrande.gemini.service.GeminiService;

import java.util.Map;

@RestController
@RequestMapping("/api/gemini")
public class GeminiController {

    @Autowired
    private GeminiService geminiService;

    @PostMapping("/generate")
    public Mono<String> generateContent(@RequestBody Map<String, String> request) {
        String prompt = request.get("text");
        return geminiService.generateContent(prompt);
    }

    // Endpoint para obtener el historial desde MongoDB
    @GetMapping("/history")
    public Flux<ApiTest> getHistory() {
        return geminiService.getHistory();
    }
    
    // Endpoint para actualizar un elemento del historial
    @PutMapping("/history/{id}")
    public Mono<ApiTest> updateHistoryItem(@PathVariable String id, @RequestBody ApiTest apiTest) {
        return geminiService.updateHistoryItem(id, apiTest);
    }

    // Endpoint para eliminar lógicamente un elemento del historial
    @DeleteMapping("/history/{id}")
    public Mono<ApiTest> logicalDeleteHistoryItem(@PathVariable String id) {
        return geminiService.logicalDeleteHistoryItem(id);
    }

    // Endpoint para restaurar un elemento del historial eliminado lógicamente
    @PostMapping("/history/{id}/restore")
    public Mono<ApiTest> restoreHistoryItem(@PathVariable String id) {
        return geminiService.restoreHistoryItem(id);
    }

    // Endpoint para eliminar permanentemente un elemento del historial
    @DeleteMapping("/history/permanent/{id}")
    public Mono<Void> deleteHistoryItem(@PathVariable String id) {
        return geminiService.deleteHistoryItem(id);
    }

    // Endpoint para obtener todo el historial, incluyendo los eliminados lógicamente
    @GetMapping("/history/all")
    public Flux<ApiTest> getAllHistoryIncludingDeleted() {
        return geminiService.getAllHistoryIncludingDeleted();
    }

    @PutMapping("/edit/{id}")
    public Mono<String> editContent(@PathVariable String id, @RequestBody Map<String, String> request) {
        String newPrompt = request.get("text");
        return geminiService.editContent(id, newPrompt);
    }
}