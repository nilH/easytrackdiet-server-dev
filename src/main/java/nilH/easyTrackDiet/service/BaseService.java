package nilH.easyTrackDiet.service;


import nilH.easyTrackDiet.model.User;
import reactor.core.publisher.Mono;

public interface BaseService {
    Mono<User> getUser(int user_id);
    Mono<User> findUserByEmail(String email);
    Mono<User> createCustomer(User user);
    Mono<User> createAdmin(User user);
}
