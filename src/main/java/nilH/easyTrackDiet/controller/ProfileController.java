package nilH.easyTrackDiet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nilH.easyTrackDiet.dto.SignupFormData;
import nilH.easyTrackDiet.model.User;
import nilH.easyTrackDiet.service.BaseService;

@RestController
@RequestMapping("/forms/profile")
public class ProfileController {
    @Autowired
    BaseService baseService;

    @GetMapping(value = "/test")
    public SignupFormData gettest(){
        return new SignupFormData();
    }

    @GetMapping(value="/getUserInfo")
    public SignupFormData getProfile(){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String email=(String)authentication.getPrincipal();
        if(email==null){
            throw new NullPointerException("null principle in authentication");
        }
        User user=baseService.findUserByEmail(email);
        if(user==null){
            throw new NullPointerException("get null user profile");
        }
        return new SignupFormData("null",user.getEmail(),user.getPwd(),user.getWeight(),user.getHeight());
    }
}
