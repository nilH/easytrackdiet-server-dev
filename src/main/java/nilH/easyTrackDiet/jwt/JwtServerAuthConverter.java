package nilH.easyTrackDiet.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class JwtServerAuthConverter implements ServerAuthenticationConverter {
    private Logger logger = LoggerFactory.getLogger(JwtServerAuthConverter.class);
    @Autowired
    private JWTEncryption encryption;
    @Autowired
    private ReactiveUserDetailsService userDetailsService;

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (authHeader != null && !authHeader.isBlank() && authHeader.startsWith("Bearer ")) {
            String jwtToken = authHeader.substring(7);
            if (jwtToken == null || jwtToken.isBlank()) {
                logger.info("no jwt token in header in filter");
                exchange.getResponse().setStatusCode(HttpStatus.NON_AUTHORITATIVE_INFORMATION);
                exchange.getResponse().getHeaders().set("error", "No JWT Token in bearer auth header");

            } else {
                String email = encryption.validateJWTAndGetEmail(jwtToken);
                Mono<UserDetails> userDetail = userDetailsService.findByUsername(email);
                return userDetail.map(u -> {
                    logger.info("jwt filter converter userdetail "+u.getUsername()+" "+String.valueOf(u.getAuthorities().toArray(new SimpleGrantedAuthority[0])[1].getAuthority()));
                    return new UsernamePasswordAuthenticationToken(u.getUsername(),u.getPassword(), u.getAuthorities());
                });
            }
        } else {
            logger.info("invalid auth header");
            return Mono.error(new Throwable("invalid auth header"));
        }
        return null;
    }

}
