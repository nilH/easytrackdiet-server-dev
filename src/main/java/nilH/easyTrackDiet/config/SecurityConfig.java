package nilH.easyTrackDiet.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
 
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
        .passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
        .antMatchers("/resources/**")
        .antMatchers("/webjars/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
        .antMatchers("/").permitAll()
        .antMatchers("/login").permitAll()
        .antMatchers("/logout").permitAll()
        .antMatchers("/signup/**").permitAll()
        .antMatchers("error").permitAll()
        .antMatchers("admin").permitAll()
        .antMatchers("/**").hasRole("user")

        .and().exceptionHandling().accessDeniedPage("/error/403")

        .and().formLogin()
        .loginPage("/login/form")
        .loginProcessingUrl("/auth")
        .failureUrl("/login/form?error")
        .usernameParameter("username")
        .passwordParameter("password")
        .defaultSuccessUrl("/default",true)

        .and().logout()
        .logoutUrl("/logout")
        .logoutSuccessUrl("/login/form?logoutSuccess")
        .permitAll()

        .and().anonymous()
        .and().csrf().disable();

    }

 
    public PasswordEncoder passwordEncoder(){ 
        return new BCryptPasswordEncoder(4);
    }
}
