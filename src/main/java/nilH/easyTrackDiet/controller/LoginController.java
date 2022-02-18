package nilH.easyTrackDiet.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import nilH.easyTrackDiet.dto.LoginFormData;

@RestController
public class LoginController {
    @GetMapping(value = "/login")
    public LoginFormData loginFormReturn(@RequestParam(value="error",required = false) String error,@RequestParam(value = "logoutSuccess ",required = false) String logout){
        LoginFormData data=new LoginFormData(null,null);
        if(error!=null){
            data.setErrorDisplay("authntication error");
        }
        if(logout!=null){
            data.setMessageDisplay("logout success");
        }
        return data;
    }
}
