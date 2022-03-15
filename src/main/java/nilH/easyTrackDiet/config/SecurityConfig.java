package nilH.easyTrackDiet.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;

import nilH.easyTrackDiet.jwt.JwtAuthManager;
import nilH.easyTrackDiet.jwt.JwtServerAuthConverter;
 
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig{

    @Autowired
    private JwtAuthManager jwtAuthManager;
    @Autowired
    private   JwtServerAuthConverter converter;
    @Bean
    protected SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http){
        return http.authorizeExchange()
        .pathMatchers("/login").permitAll()
        .pathMatchers("/logout").permitAll()
        .pathMatchers("/signup/**").permitAll()
        .pathMatchers("/error").permitAll()
        .pathMatchers("/forms/profile/**").hasAuthority("0")
        .and().addFilterAt(jwtAuthFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
        .httpBasic().disable()
        .csrf().disable()
        .formLogin().disable()
        .build();
    }

    private AuthenticationWebFilter jwtAuthFilter(){
        AuthenticationWebFilter filter=new AuthenticationWebFilter(jwtAuthManager);
        filter.setServerAuthenticationConverter(converter);
        filter.setRequiresAuthenticationMatcher(ServerWebExchangeMatchers.pathMatchers("/forms/**"));
        return filter;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){ 
        return new BCryptPasswordEncoder(4);
    }

}
