package nilH.easyTrackDiet.dto;

public class TokenData {
    String jwtToken;
    String signupError;
    public TokenData(){}
    public TokenData(String jwtToken, String signupError){
        this.jwtToken=jwtToken;
        this.signupError=signupError;
    }
    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
    public String getJwtToken() {
        return jwtToken;
    }
    public void setSignupError(String signupError) {
        this.signupError = signupError;
    }
    public String getSignupError() {
        return signupError;
    }
}
