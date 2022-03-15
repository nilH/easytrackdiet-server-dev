package nilH.easyTrackDiet.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import nilH.easyTrackDiet.dto.LoginFormData;
import nilH.easyTrackDiet.dto.TokenData;
import nilH.easyTrackDiet.jwt.JWTEncryption;
import reactor.core.publisher.Mono;

@RestController
public class LoginController {
    @Autowired
    private JWTEncryption jwtEncryption;
    @Autowired
    private ReactiveUserDetailsService userDetailsService;
    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @PostMapping(value = "/login")
    public Mono<TokenData> loginFormReturn(@RequestBody LoginFormData data) {
        if (data.getEmail() == null) {
            return Mono.error(new Throwable("no email provided"));
        }
        if (data.getPassword() == null) {
            return Mono.error(new Throwable("no password provided"));
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                data.getEmail(), data.getPassword());
        UserDetailsRepositoryReactiveAuthenticationManager authenticationManager = new UserDetailsRepositoryReactiveAuthenticationManager(
                userDetailsService);
        authenticationManager.setPasswordEncoder(new BCryptPasswordEncoder());
        return authenticationManager.authenticate(authenticationToken).handle((auth, sink) -> {
            logger.info("login auth " + auth.getPrincipal().toString());
            if (auth.getPrincipal() != null) {
                sink.next(auth);
            } else {
                sink.error(new Throwable("wrong password"));
            }
        }).map(auth -> {
            String token = jwtEncryption.createJWTTokenByEmail(data.getEmail());
            return new TokenData(token, null);
        });

    }
}
