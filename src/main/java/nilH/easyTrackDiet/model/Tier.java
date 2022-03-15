package nilH.easyTrackDiet.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

//domain name: food, ingredient 
@Table(value = "atier")
public class Tier {
    @Id
    private int tier_id;
    private String domain;
    private String tierpath;
    public Tier(){}
    public Tier(String domin, String tierpath){
        this.domain=domin;
        this.tierpath=tierpath;
    }
    public void setTier_id(int tier_id) {
        this.tier_id = tier_id;
    }
    public int getTier_id() {
        return tier_id;
    }
    public void setDomain(String domain) {
        this.domain = domain;
    }
    public String getDomain() {
        return domain;
    }
    public void setTierpath(String tierpath) {
        this.tierpath = tierpath;
    }
    public String getTierpath() {
        return tierpath;
    }
}
