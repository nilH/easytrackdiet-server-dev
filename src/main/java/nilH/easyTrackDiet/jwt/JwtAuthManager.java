package nilH.easyTrackDiet.jwt;


import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;

import org.springframework.stereotype.Component;


import reactor.core.publisher.Mono;

@Component
public class JwtAuthManager implements ReactiveAuthenticationManager{
 
    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.just(authentication);
    }

}
