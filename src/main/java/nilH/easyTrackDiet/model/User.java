package nilH.easyTrackDiet.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;



@Table(value = "auser")
public class User implements Serializable {
    @Id
    private int user_id;
    private String email;
    private String pwd;
    private int weight;
    private int height;
    private int[] role_ids;
    public User(){};
    public User(String email, String pwd, int height, int weight){
        this.email=email;
        this.pwd=pwd;
        this.height=height;
        this.weight=weight;
    }
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    public int getUser_id() {
        return user_id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPwd() {
        return pwd;
    }
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    public int getWeight() {
        return weight;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public int[] getRole_ids() {
        return role_ids;
    }
    public void setRole_idS(int[] role_idS) {
        this.role_ids = role_idS;
    }
}
