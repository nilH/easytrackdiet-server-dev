package nilH.easyTrackDiet.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;



//role_id=0 user; role_id=1 admin
@Table
public class Role implements Serializable{
    @Id
    private Integer role_id;
    private String name;

    public Integer getRole_id() {
        return role_id;
    }
    public void setRole_id(Integer role_id) {
        this.role_id = role_id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

}
