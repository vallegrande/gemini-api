package vallegrande.gemini.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import vallegrande.gemini.model.Translation;
import vallegrande.gemini.model.TranslationRequest;
import vallegrande.gemini.model.TranslationResponse;
import vallegrande.gemini.repository.TranslationRepository;

import java.util.Map;

@Service
public class TranslationService {

    private static final String API_URL = "https://google-translate113.p.rapidapi.com/api/v1/translator/text";
    private static final String RAPID_API_KEY = "5accced3a5msh4371f7d45c4866ap18200cjsn4add43d75521";
    private static final String RAPID_API_HOST = "google-translate113.p.rapidapi.com";
    
    private final WebClient webClient;
    
    @Autowired
    private TranslationRepository translationRepository;
    
    public TranslationService() {
        this.webClient = WebClient.builder().baseUrl(API_URL).build();
    }
    
    public Mono<String> translateText(TranslationRequest request) {
        return webClient
            .post()
            .contentType(MediaType.APPLICATION_JSON)
            .header("X-RapidAPI-Key", RAPID_API_KEY)
            .header("X-RapidAPI-Host", RAPID_API_HOST)
            .bodyValue(request)
            .retrieve()
            .bodyToMono(Map.class)
            .map(response -> {
                if (response != null && response.containsKey("trans")) {
                    String translatedText = (String) response.get("trans");
                    
                    // Guardar la traducción en MongoDB
                    Translation translation = new Translation(
                        request.getText(),
                        translatedText,
                        request.getFrom(),
                        request.getTo()
                    );
                    
                    translationRepository.save(translation).subscribe();
                    
                    return translatedText;
                }
                return "Error en la traducción";
            })
            .onErrorResume(e -> {
                e.printStackTrace();
                return Mono.just("Error al traducir: " + e.getMessage());
            });
    }
    
    public Flux<Translation> getAllTranslations() {
        return translationRepository.findAllByOrderByCreatedAtDesc();
    }
    
    public Mono<Void> deleteAllTranslations() {
        return translationRepository.deleteAll();
    }
}