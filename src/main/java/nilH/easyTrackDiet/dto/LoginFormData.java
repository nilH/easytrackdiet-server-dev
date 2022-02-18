package nilH.easyTrackDiet.dto;


public class LoginFormData {

    private String errorDisplay;
    private String messageDisplay;

    public LoginFormData(){}
    public LoginFormData(String errorDisplay, String messageDisplay){
        this.errorDisplay=errorDisplay;
        this.messageDisplay=messageDisplay;
    }

    public void setErrorDisplay(String errorDisplay) {
        this.errorDisplay = errorDisplay;
    }
    public String getErrorDisplay() {
        return errorDisplay;
    }
    public void setMessageDisplay(String messageDisplay) {
        this.messageDisplay = messageDisplay;
    }
    public String getMessageDisplay() {
        return messageDisplay;
    }
}
