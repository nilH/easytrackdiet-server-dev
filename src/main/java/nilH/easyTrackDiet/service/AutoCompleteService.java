package nilH.easyTrackDiet.service;



import nilH.easyTrackDiet.dto.TierData;
import nilH.easyTrackDiet.model.Tier;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AutoCompleteService {
    public Mono<TierData> loadTierFromDatabase(String domain);
    public Flux<TierData> loadAllTiersFromDataBase();
    public Mono<TierData> getTier(String domain);
    public Mono<TierData> updateTier(TierData ntree, String domain);
    public Mono<Tier> saveTierToDatabase(String domain);
}
