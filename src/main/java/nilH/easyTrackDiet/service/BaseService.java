package nilH.easyTrackDiet.service;


import nilH.easyTrackDiet.model.User;

public interface BaseService {
    User getUser(int user_id);
    User findUserByEmail(String email);
    int createCustomer(User user);
    int createAdmin(User user);
}
