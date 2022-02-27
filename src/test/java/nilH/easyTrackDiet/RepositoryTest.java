package nilH.easyTrackDiet;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;

import nilH.easyTrackDiet.model.User;
import nilH.easyTrackDiet.repository.UserRepository;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@AutoConfigureWebTestClient
@SpringBootTest
public class RepositoryTest {
    @Autowired
    private UserRepository repository;

    @Test
    public void getUserInfo(){
        Mono<User> mono=repository.findByEmail("email8");
        StepVerifier.create(mono).expectNextCount(0).verifyComplete();
    }
}
