package vallegrande.gemini.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import vallegrande.gemini.model.ApiTest;

@Repository
public interface ApiTestRepository extends ReactiveMongoRepository<ApiTest, String> {
    
    Flux<ApiTest> findAllByOrderByTimestampDesc();
    Flux<ApiTest> findByDeletedFalse();
}