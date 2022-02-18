package nilH.easyTrackDiet.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import nilH.easyTrackDiet.model.User;

//Set and Get current user of one session, through security context, and get sync with database
@Component
public class SecurityUserContext {
    private UserDetailsService userDetailsService;
    private static Logger logger=LoggerFactory.getLogger(SecurityUserContext.class);
    @Autowired
    public SecurityUserContext(UserDetailsService userDetailsService){
        this.userDetailsService=userDetailsService;
    }

    public User getCurrentUser(){
        SecurityContext securityContext=SecurityContextHolder.getContext();
        Authentication authentication=securityContext.getAuthentication();
        if(authentication==null){
            logger.info("get current user as null auth ");
            return null;
        }
        User user=(User)authentication.getPrincipal();
        User dbUser=(User)this.userDetailsService.loadUserByUsername(user.getEmail());
        if(dbUser==null){
            throw new IllegalStateException("current user not sync with database "+user.getEmail());
        }
        return dbUser;
    }

    public void setCurrentUser(User user){
        if(user==null){
            throw new IllegalArgumentException("null user");
        }
        UserDetails userDetails=userDetailsService.loadUserByUsername(user.getEmail());
        UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
