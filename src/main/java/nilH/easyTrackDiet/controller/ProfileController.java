package nilH.easyTrackDiet.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nilH.easyTrackDiet.dto.SignupFormData;
import nilH.easyTrackDiet.model.User;
import nilH.easyTrackDiet.service.BaseService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/forms/profile")
public class ProfileController {
    @Autowired
    BaseService baseService;
    private Logger logger = LoggerFactory.getLogger(ProfileController.class);

    @GetMapping(value = "/test")
    public SignupFormData gettest() {
        return new SignupFormData();
    }

    @GetMapping(value = "/getUserInfo")
    public Mono<User> getProfile() {
        return ReactiveSecurityContextHolder.getContext().filter(context -> context.getAuthentication() != null)
                .map(context -> {
                    return (String) context.getAuthentication().getPrincipal();
                }).flatMap(email -> {
                    logger.info("getprofile controller email " + email);
                    return baseService.findUserByEmail(email)
                            .switchIfEmpty(Mono.error(new Throwable("user email not found")));
                });
    }
}
