package nilH.easyTrackDiet.dao;

import java.util.HashSet;
import java.util.Set;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import nilH.easyTrackDiet.model.Role;
import nilH.easyTrackDiet.model.User;
import nilH.easyTrackDiet.repository.RoleRepository;
import nilH.easyTrackDiet.repository.UserRepository;

@Repository
public class UserDaoImpl implements UserDao {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    @Autowired
    public UserDaoImpl(UserRepository userRepository, RoleRepository roleRepository){
        this.userRepository=userRepository;
        this.roleRepository=roleRepository;
    }
    @Override
    public User getUser(int user_id) {
        return userRepository.findById(user_id).orElse(null);
    }
    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    @Override
    public int createUser(User user, int[] role_idS) {
        if(user==null){
            throw new IllegalArgumentException("user to create is null");
        }
        Set<Role> roles=new HashSet<Role>();
        for(int role_id:role_idS){
            roles.add(roleRepository.findById(role_id).orElse(null));
        }
        user.setRoles(roles);
        userRepository.save(user);
        userRepository.flush();
        return user.getUserId();
    }
}
