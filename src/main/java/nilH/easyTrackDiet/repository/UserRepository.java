package nilH.easyTrackDiet.repository;


import org.springframework.data.r2dbc.repository.R2dbcRepository;

import nilH.easyTrackDiet.model.User;
import reactor.core.publisher.Mono;

public interface UserRepository extends R2dbcRepository<User,Integer> {
    Mono<User> findByEmail(String email);
}
