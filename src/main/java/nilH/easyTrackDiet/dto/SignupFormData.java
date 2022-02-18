package nilH.easyTrackDiet.dto;

//add one role everytime signing up
public class SignupFormData {
    private String role;
    private String email;
    private String password;
    private int weight;
    private int height;
    public SignupFormData(){}
    public SignupFormData(String role, String email, String password, int weight, int height){
        this.role=role;
        this.email=email;
        this.password=password;
        this.weight=weight;
        this.height=height;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public String getRole() {
        return role;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }
    public int getWeight() {
        return weight;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public int getHeight() {
        return height;
    }
}
