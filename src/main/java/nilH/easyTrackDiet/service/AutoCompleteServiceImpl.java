package nilH.easyTrackDiet.service;

import java.util.Iterator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.stereotype.Service;

import nilH.easyTrackDiet.dto.TierData;
import nilH.easyTrackDiet.model.Tier;
import nilH.easyTrackDiet.repository.TierRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AutoCompleteServiceImpl implements AutoCompleteService{
    private Logger logger=LoggerFactory.getLogger(AutoCompleteServiceImpl.class);
    private String REDISKEY="TIER";
    @Autowired
    private ReactiveHashOperations<String,Integer,TierData> operations;
    @Autowired
    private TierRepository tierRepository;
    @Autowired
    private ObjectMapper objectMapper;
    private TierData tierToData(Tier tier){
        JsonNode tierNode;
        try{
            tierNode=objectMapper.readTree(tier.getTierpath());
            TierData tierData=new TierData(tier.getDomain(), tierNode);
        operations.put(REDISKEY, tier.getTier_id(), tierData);
        return tierData;
        }catch(JsonProcessingException e){
            logger.error("invalid tier json from database");
            return null;
        }
    }
    private String dataToTier(TierData tierData){
        try{
            return objectMapper.writeValueAsString(tierData.getTierPath());
        }catch(JsonProcessingException e){
            logger.error("invalid tierdata to json");
            return null;
        }
        
    }
    @Override
    public Mono<TierData> loadTierFromDatabase(String domain){
        return tierRepository.findByDomain(domain).map(tier->{
            return tierToData(tier);
        });
    }
    @Override
    public Flux<TierData> loadAllTiersFromDataBase() {
        return tierRepository.findAll().map(tier->{
            return tierToData(tier);
        });
    }
    @Override
    public Mono<Tier> saveTierToDatabase(String domain) {
        return operations.get(REDISKEY, domain).flatMap(tierData->{
            return tierRepository.save(new Tier(tierData.getDomain(),dataToTier(tierData)));
        });
    }
    @Override
    public Mono<TierData> getTier(String domain) {
        return operations.get(REDISKEY, domain).switchIfEmpty(loadTierFromDatabase(domain));
    }
    private void addNode(ObjectNode ntree, ObjectNode tree){
        ObjectNode child;
        Iterator<String> names=ntree.fieldNames();
        while(names.hasNext()){
            String nodeName=names.next();
            child=(ObjectNode)tree.get(nodeName);
            if(child==null){
                tree.set(nodeName, ntree.get(nodeName));
            }
            addNode((ObjectNode)ntree.get(nodeName), child);
        }
    }
    @Override
    public Mono<TierData> updateTier(TierData ntree, String domain) {
        return operations.get(REDISKEY, domain).map(tierData->{
            ObjectNode ntreeRoot=(ObjectNode)ntree.getTierPath();
            ObjectNode treeRoot=(ObjectNode)tierData.getTierPath();
            addNode(ntreeRoot, treeRoot);
            tierData.setTierPath(treeRoot);
            return tierData;
        });
    }
}
