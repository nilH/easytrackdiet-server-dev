package nilH.easyTrackDiet.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;



@Table
public class User implements Serializable {
    @Id
    private int user_id;
    private String email;
    private String pwd;
    private int weight;
    private int height;
    private int[] role_idS;
    public User(){};
    public User(String email, String pwd, int height, int weight){
        this.email=email;
        this.pwd=pwd;
        this.height=height;
        this.weight=weight;
    }
    public Integer getUserId() {
        return user_id;
    }
    public void setUserId(Integer userId) {
        this.user_id = userId;
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
    public int[] getRole_idS() {
        return role_idS;
    }
    public void setRole_idS(int[] role_idS) {
        this.role_idS = role_idS;
    }
}
