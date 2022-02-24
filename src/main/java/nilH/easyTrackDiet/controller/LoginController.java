package nilH.easyTrackDiet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import nilH.easyTrackDiet.dto.LoginFormData;
import nilH.easyTrackDiet.dto.TokenData;
import nilH.easyTrackDiet.util.JWTEncryption;
import reactor.core.publisher.Mono;

@RestController
public class LoginController {
    @Autowired
    private JWTEncryption jwtEncryption;
    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping(value = "/login")
    public Mono<TokenData> loginFormReturn(@RequestBody LoginFormData data) {
        if (data.getEmail() == null) {
            return Mono.just(new TokenData(null, "no email provided"));
        }
        if (data.getPassword() == null) {
            return Mono.just(new TokenData(null, "no password provided"));
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                data.getEmail(), data.getPassword());
        try {
            authenticationManager.authenticate(authenticationToken);
        } catch (AuthenticationException exception) {
            return Mono.just(new TokenData(null, "wrong password"));
        }
        String token = jwtEncryption.createJWTTokenByEmail(data.getEmail());
        return Mono.just(new TokenData(token, null));
    }
}
