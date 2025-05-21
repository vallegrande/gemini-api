package vallegrande.gemini.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import vallegrande.gemini.model.Translation;
import vallegrande.gemini.model.TranslationRequest;
import vallegrande.gemini.service.TranslationService;

@RestController
@RequestMapping("/api/translation")
public class TranslationController {

    @Autowired
    private TranslationService translationService;

    @PostMapping("/translate")
    public Mono<String> translateText(@RequestBody TranslationRequest request) {
        return translationService.translateText(request);
    }
    
    @GetMapping("/history")
    public Flux<Translation> getTranslationHistory() {
        return translationService.getAllTranslations();
    }
    
    @DeleteMapping("/clear")
    public Mono<Void> clearTranslationHistory() {
        return translationService.deleteAllTranslations();
    }
}