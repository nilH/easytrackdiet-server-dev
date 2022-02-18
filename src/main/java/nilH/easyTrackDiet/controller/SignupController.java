package nilH.easyTrackDiet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import nilH.easyTrackDiet.dto.SignupFormData;
import nilH.easyTrackDiet.model.User;
import nilH.easyTrackDiet.service.BaseService;
import nilH.easyTrackDiet.service.SecurityUserContext;

@Controller
@RequestMapping(value = "signup")
public class SignupController {
    private final SecurityUserContext securityUserContext;
    private final BaseService baseService;
    @Autowired
    public SignupController(SecurityUserContext securityUserContext, BaseService baseService){
        if(securityUserContext==null){
            throw new IllegalArgumentException("securityUserContext is null");
        }
        if(baseService==null){
            throw new IllegalArgumentException("baseService is null");
        }
        this.securityUserContext=securityUserContext;
        this.baseService=baseService;
    }

    @PostMapping(value = "newUser")
    public String signupFormPost(@RequestBody SignupFormData data, RedirectAttributes redirectAttributes){
        String email=data.getEmail();
        if(baseService.findUserByEmail(email)!=null){
            redirectAttributes.addFlashAttribute("display error", "email already exists");
        }
        User user=new User(email,data.getPassword(),data.getHeight(),data.getWeight());
        int user_id;
        if(data.getRole().equals("admin")){
            user_id=baseService.createAdmin(user);
        }else if(data.getRole().equals("user")){
            user_id=baseService.createCustomer(user);
        }else{
            throw new IllegalArgumentException("wrong role token from signup form "+data.getRole());
        }
        user.setUserId(user_id);
        securityUserContext.setCurrentUser(user);
        redirectAttributes.addFlashAttribute("display message","sign up success");
        return "redirect:/";
    }
}
