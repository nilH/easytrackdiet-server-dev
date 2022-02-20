package nilH.easyTrackDiet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import nilH.easyTrackDiet.dto.LoginFormData;
import nilH.easyTrackDiet.dto.TokenData;
import nilH.easyTrackDiet.util.JWTEncryption;

@RestController
public class LoginController {
    @Autowired
    private JWTEncryption jwtEncryption;
    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping(value = "/login")
    public TokenData loginFormReturn(@RequestBody LoginFormData data){
        if(data.getEmail()==null){
            return new TokenData(null, "no email provided");
        }
        if(data.getPassword()==null){
            return new TokenData(null, "no password provided");
        }
        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(data.getEmail(), data.getPassword());
        try{
            authenticationManager.authenticate(authenticationToken);
        }catch(AuthenticationException exception){
            return new TokenData(null,"wrong password");
        }
        String token=jwtEncryption.createJWTTokenByEmail(data.getEmail());
        return new TokenData(token,null);
    }
}
