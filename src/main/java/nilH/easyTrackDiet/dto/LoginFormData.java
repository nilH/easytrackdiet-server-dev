package nilH.easyTrackDiet.dto;


public class LoginFormData {

    private String email;
    private String password;

    public LoginFormData(){}
    public LoginFormData(String email, String password){
        this.email=email;
        this.password=password;
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
}
