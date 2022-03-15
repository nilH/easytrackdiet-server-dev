package nilH.easyTrackDiet.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import nilH.easyTrackDiet.model.Tier;
import reactor.core.publisher.Mono;

public interface TierRepository extends R2dbcRepository<Tier,Integer> {
    Mono<Tier> findByDomain(String domain);
}
