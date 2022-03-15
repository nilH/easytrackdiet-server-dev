package nilH.easyTrackDiet.dto;

import com.fasterxml.jackson.databind.JsonNode;

//Jsonnode schema like this node: {a:node, b:node, c:node, freq: int}
public class TierData {
    String domain;
    JsonNode tierPath;
    public TierData(){}
    public TierData(String domain, JsonNode tierPath){
        this.domain=domain;
        this.tierPath=tierPath;
    }
    public String getDomain() {
        return domain;
    }
    public void setDomain(String domain) {
        this.domain = domain;
    }
    public JsonNode getTierPath() {
        return tierPath;
    }
    public void setTierPath(JsonNode tierPath) {
        this.tierPath = tierPath;
    }
}
