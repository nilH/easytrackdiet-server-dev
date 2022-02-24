package nilH.easyTrackDiet.dao;

import nilH.easyTrackDiet.model.User;
import reactor.core.publisher.Mono;

public interface UserDao {
    Mono<User> getUser(int user_id);
    Mono<User> findUserByEmail(String email);
    Mono<User> createUser(User user, int[] role_ids);
}
