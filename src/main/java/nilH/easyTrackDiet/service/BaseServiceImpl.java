package nilH.easyTrackDiet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import nilH.easyTrackDiet.dao.UserDao;
import nilH.easyTrackDiet.model.User;

@Repository
public class BaseServiceImpl implements BaseService{
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public BaseServiceImpl(final UserDao userDao){
        this.userDao=userDao;
        this.passwordEncoder=new BCryptPasswordEncoder(4);
    }
    @Override
    public User getUser(int user_id) {
        return userDao.getUser(user_id);
    }
    @Override
    public User findUserByEmail(String email) {
        return userDao.findUserByEmail(email);
    }
    @Override
    public int createCustomer(User user) {
        String encodedPwd=passwordEncoder.encode(user.getPwd());
        user.setPwd(encodedPwd);
        return userDao.createUser(user,new int[]{0}); 
    }
    @Override
    public int createAdmin(User user) {
        String encodedPwd=passwordEncoder.encode(user.getPwd());
        user.setPwd(encodedPwd);
        return userDao.createUser(user,new int[]{0,1}); 
    }
}
