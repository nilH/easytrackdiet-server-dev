package nilH.easyTrackDiet.dao;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import nilH.easyTrackDiet.model.User;
import nilH.easyTrackDiet.repository.UserRepository;
import reactor.core.publisher.Mono;

@Repository
public class UserDaoImpl implements UserDao {
    private UserRepository userRepository;
    private Logger logger=LoggerFactory.getLogger(UserDaoImpl.class);
    @Autowired
    public UserDaoImpl(UserRepository userRepository){
        this.userRepository=userRepository;
    }
    @Override
    public Mono<User> getUser(int user_id) {
        return userRepository.findById(user_id);
    }
    @Override
    public Mono<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email).map(u->{
            logger.info("finduserbyemail  "+u.getEmail());
            return u;
        }
        );
    }
    @Transactional
    @Override
    public Mono<User> createUser(User user, int[] role_idS) {
        if(user==null){
            throw new IllegalArgumentException("user to create is null");
        }
        user.setRole_idS(role_idS);
        return userRepository.save(user);
    }
}
