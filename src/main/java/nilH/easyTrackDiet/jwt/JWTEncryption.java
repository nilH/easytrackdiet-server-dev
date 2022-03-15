package nilH.easyTrackDiet.jwt;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JWTEncryption {
    @Value("${jwt_secret}")
    private String secret;

    public String createJWTTokenByEmail(String email) throws IllegalArgumentException, JWTCreationException{
        return JWT.create().withSubject("user_info").withClaim("email", email)
        .withIssuedAt(new Date()).withIssuer("easyTrackDiet").sign(Algorithm.HMAC256(secret));
    }

    public String validateJWTAndGetEmail(String token) throws JWTVerificationException{
        JWTVerifier verifier=JWT.require((Algorithm.HMAC256(secret))).withIssuer("easyTrackDiet").build();
        DecodedJWT jwt=verifier.verify(token);
        return jwt.getClaim("email").asString();
    }
}
