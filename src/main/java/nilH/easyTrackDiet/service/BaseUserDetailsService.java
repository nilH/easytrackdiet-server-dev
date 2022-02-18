package nilH.easyTrackDiet.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import nilH.easyTrackDiet.dao.UserDao;
import nilH.easyTrackDiet.model.Role;
import nilH.easyTrackDiet.model.User;

//user service for spring security
//encapsulate an extended User class as UserDetail for security use
@Component
public class BaseUserDetailsService implements UserDetailsService {
    private final UserDao userDao;

    @Autowired
    public BaseUserDetailsService(UserDao userDao){
        if(userDao==null){
            throw new IllegalArgumentException("null userdao");
        }
        this.userDao=userDao;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user= userDao.findUserByEmail(username);
        if(user==null){
            throw new UsernameNotFoundException("email not found");
        }
        return new BaseUserDetails(user);
    }

    private class BaseUserDetails extends User implements UserDetails{
        BaseUserDetails(User user){
            setUserId(user.getUserId());
            setEmail(user.getEmail());
            setPwd(user.getPwd());
            setHeight(user.getHeight());
            setWeight(user.getWeight());
            setRoles(user.getRoles());
        }
        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            Set<Role> roles= this.getRoles();
            List<GrantedAuthority> authorities=new ArrayList<GrantedAuthority>();
            roles.forEach((role) ->authorities.add(new SimpleGrantedAuthority(role.getName())) );
            return authorities;
        }
        @Override
        public String getUsername() {
            return getEmail();
        }
        @Override
        public boolean isEnabled() {
            return true;
        }
        @Override
        public String getPassword() {
            return getPwd();
        }
        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }
        @Override
        public boolean isAccountNonExpired() {
            return true;
        }
        @Override
        public boolean isAccountNonLocked() {
            return true; 
        }
    }
}
