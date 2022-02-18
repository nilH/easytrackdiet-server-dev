package nilH.easyTrackDiet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nilH.easyTrackDiet.dto.SignupFormData;
import nilH.easyTrackDiet.model.User;
import nilH.easyTrackDiet.service.SecurityUserContext;

@RestController
@RequestMapping("profile")
public class ProfileController {
    private final SecurityUserContext securityUserContext;
    @Autowired
    public ProfileController(SecurityUserContext securityUserContext){
        if(securityUserContext==null){
            throw new IllegalArgumentException("securityUserContext is null");
        }
        this.securityUserContext=securityUserContext;
    }
    @GetMapping(value="getUserInfo")
    public SignupFormData getProfile(){
        User user=securityUserContext.getCurrentUser();
        if(user==null){
            throw new NullPointerException("get null user profile");
        }
        return new SignupFormData("null",user.getEmail(),user.getPwd(),user.getWeight(),user.getHeight());
    }
}
