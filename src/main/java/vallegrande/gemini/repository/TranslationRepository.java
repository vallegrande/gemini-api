package vallegrande.gemini.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import vallegrande.gemini.model.Translation;

@Repository
public interface TranslationRepository extends ReactiveMongoRepository<Translation, String> {
    Flux<Translation> findAllByOrderByCreatedAtDesc();
}