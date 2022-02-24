package nilH.easyTrackDiet.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class JwtServerAuthConverter implements ServerAuthenticationConverter {
    private Logger logger=LoggerFactory.getLogger(JwtServerAuthConverter.class);
    @Autowired
    private JWTEncryption encryption;
    @Autowired
    private UserDetailsService userDetailsService;
    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        String authHeader=exchange.getRequest().getHeaders().getFirst("Authorization");
        if (authHeader != null && !authHeader.isBlank() && authHeader.startsWith("Bearer ")) {
            String jwtToken = authHeader.substring(7);
            if (jwtToken == null || jwtToken.isBlank()) {
                logger.info("no jwt token in header in filter");
                exchange.getResponse().setStatusCode(HttpStatus.NON_AUTHORITATIVE_INFORMATION);
                exchange.getResponse().getHeaders().set("error", "No JWT Token in bearer auth header");

            } else {
                String email=encryption.validateJWTAndGetEmail(jwtToken);
                UserDetails userDetail=userDetailsService.loadUserByUsername(email);
                return Mono.just(new UsernamePasswordAuthenticationToken(userDetail.getUsername(), userDetail.getAuthorities()));
            }
        }else{
            logger.info("invalid auth header");
            exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
            exchange.getResponse().getHeaders().set("error", "invalid auth header");
        }
        return Mono.empty();
    }
    
}
