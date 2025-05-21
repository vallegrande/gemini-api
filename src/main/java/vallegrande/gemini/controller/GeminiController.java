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
    
    // Endpoint para eliminar un elemento del historial
    @DeleteMapping("/history/{id}")
    public Mono<Void> deleteHistoryItem(@PathVariable String id) {
        return geminiService.deleteHistoryItem(id);
    }
}