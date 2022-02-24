package nilH.easyTrackDiet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nilH.easyTrackDiet.dto.SignupFormData;
import nilH.easyTrackDiet.dto.TokenData;
import nilH.easyTrackDiet.model.User;
import nilH.easyTrackDiet.service.BaseService;
import nilH.easyTrackDiet.util.JWTEncryption;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/signup")
public class SignupController {
    private final BaseService baseService;

    @Autowired
    private JWTEncryption jwtEncryption;

    @Autowired
    public SignupController(BaseService baseService) {
        if (baseService == null) {
            throw new IllegalArgumentException("baseService is null");
        }
        this.baseService = baseService;
    }

    @PostMapping(value = "/newUser")
    public Mono<TokenData> signupFormPost(@RequestBody SignupFormData data) {
        String email = data.getEmail();
        if (baseService.findUserByEmail(email) != null) {
            return Mono.just(new TokenData(null, "email already registered"));
        }
        User user = new User(email, data.getPassword(), data.getHeight(), data.getWeight());
        if (data.getRole().equals("admin")) {
            baseService.createAdmin(user);
        } else if (data.getRole().equals("user")) {
            baseService.createCustomer(user);
        } else {
            throw new IllegalArgumentException("wrong role token from signup form " + data.getRole());
        }
        String token = jwtEncryption.createJWTTokenByEmail(email);
        return Mono.just(new TokenData(token, null));
    }

}
