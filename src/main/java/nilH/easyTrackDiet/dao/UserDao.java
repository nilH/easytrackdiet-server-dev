package nilH.easyTrackDiet.dao;

import nilH.easyTrackDiet.model.User;

public interface UserDao {
    User getUser(int user_id);
    User findUserByEmail(String email);
    int createUser(User user, int[] role_ids);
}
