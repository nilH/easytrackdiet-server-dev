package nilH.easyTrackDiet.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nilH.easyTrackDiet.dto.SignupFormData;
import nilH.easyTrackDiet.dto.TokenData;
import nilH.easyTrackDiet.jwt.JWTEncryption;
import nilH.easyTrackDiet.model.User;
import nilH.easyTrackDiet.repository.UserRepository;
import nilH.easyTrackDiet.service.BaseService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/signup")
public class SignupController {
    private final BaseService baseService;
    private Logger logger = LoggerFactory.getLogger(SignupController.class);
    @Autowired
    private JWTEncryption jwtEncryption;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    public SignupController(BaseService baseService) {
        if (baseService == null) {
            throw new IllegalArgumentException("baseService is null");
        }
        this.baseService = baseService;
    }

    @GetMapping(value="/test")
    public Mono<User> testforrep(){
        logger.info("testing controller");
        return userRepository.findById(27).switchIfEmpty(Mono.error(new Throwable("not found"))).map(u->{
            logger.info("testing controller "+u.getEmail());
            return u;
        });
    }

    @PostMapping(value = "/newUser")
    public Mono<TokenData> signupFormPost(@RequestBody SignupFormData data) {
        String email = data.getEmail();
        User user = new User(email, data.getPassword(), data.getHeight(), data.getWeight());
        logger.info("signupformpost log newuser email " + user.getEmail());
        if (data.getRole().equals("admin")) {
            String token = jwtEncryption.createJWTTokenByEmail(email);
            
            return userRepository.findByEmail(email).switchIfEmpty(Mono.defer(()->baseService.createAdmin(user) ) ).then(Mono.defer(()->Mono.just(new TokenData(token, null)))) ;
        } else if (data.getRole().equals("user")) {
            String token = jwtEncryption.createJWTTokenByEmail(email);
            return userRepository.findByEmail(email).switchIfEmpty(Mono.defer(()->baseService.createCustomer(user) ) ).then(Mono.defer(()->Mono.just(new TokenData(token, null)))) ;
        }
        return Mono.just(new TokenData(null,"invalid role provided"));
    }


}
