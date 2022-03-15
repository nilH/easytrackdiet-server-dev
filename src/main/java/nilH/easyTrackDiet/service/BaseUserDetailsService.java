package nilH.easyTrackDiet.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import nilH.easyTrackDiet.dao.UserDao;

import nilH.easyTrackDiet.model.User;
import reactor.core.publisher.Mono;

//user service for spring security
//encapsulate an extended User class as UserDetail for security use
@Service
public class BaseUserDetailsService implements ReactiveUserDetailsService {
    private final UserDao userDao;

    @Autowired
    public BaseUserDetailsService(UserDao userDao){
        if(userDao==null){
            throw new IllegalArgumentException("null userdao");
        }
        this.userDao=userDao;
    }
    @Override
    public Mono<UserDetails> findByUsername(String username) throws UsernameNotFoundException{
        Mono<User> user= userDao.findUserByEmail(username);
        return user.map(u->{
            if(u==null){
                throw new UsernameNotFoundException("email not found");
            }else{
                return new BaseUserDetails(u);
            }
        });
    }


    private class BaseUserDetails extends User implements UserDetails{
        BaseUserDetails(User user){
            setUser_id(user.getUser_id());
            setEmail(user.getEmail());
            setPwd(user.getPwd());
            setHeight(user.getHeight());
            setWeight(user.getWeight());
            setRole_idS(user.getRole_ids());
        }
        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            int[] role_idS= this.getRole_ids();
            List<GrantedAuthority> authorities=new ArrayList<GrantedAuthority>();
            for(int role_id: role_idS){
                authorities.add(new SimpleGrantedAuthority(String.valueOf(role_id)));
            }
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
