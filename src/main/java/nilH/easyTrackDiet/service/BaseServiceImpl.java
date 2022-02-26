package nilH.easyTrackDiet.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import nilH.easyTrackDiet.dao.UserDao;
import nilH.easyTrackDiet.model.User;
import reactor.core.publisher.Mono;

@Repository
public class BaseServiceImpl implements BaseService{
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);
    @Autowired
    public BaseServiceImpl(final UserDao userDao){
        this.userDao=userDao;
        this.passwordEncoder=new BCryptPasswordEncoder(4);
    }
    @Override
    public Mono<User> getUser(int user_id) {
        return userDao.getUser(user_id);
    }
    @Override
    public Mono<User> findUserByEmail(String email) {
        logger.info("base service find user by email "+email);
        return userDao.findUserByEmail(email);
    }
    @Override
    public Mono<User> createCustomer(User user) {
        String encodedPwd=passwordEncoder.encode(user.getPwd());
        user.setPwd(encodedPwd);
        return userDao.createUser(user,new int[]{0}); 
    }
    @Override
    public Mono<User> createAdmin(User user) {
        String encodedPwd=passwordEncoder.encode(user.getPwd());
        user.setPwd(encodedPwd);
        return userDao.createUser(user,new int[]{0,1}); 
    }
}
