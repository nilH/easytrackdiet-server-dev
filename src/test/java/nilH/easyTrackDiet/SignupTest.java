package nilH.easyTrackDiet;

import org.hamcrest.core.StringStartsWith;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import nilH.easyTrackDiet.dto.SignupFormData;
import nilH.easyTrackDiet.dto.TokenData;
import reactor.core.publisher.Mono;

import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@AutoConfigureWebTestClient
@SpringBootTest
public class SignupTest {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void signupReturn() throws Exception {

        SignupFormData data = new SignupFormData("admin", "emai22", "password1", 50, 150);
        // webTestClient.post().uri("/signup/newUser").accept(MediaType.APPLICATION_JSON)
        // .body(Mono.just(data),
        // SignupFormData.class).exchange().expectStatus().isOk();

        webTestClient.post().uri("/signup/newUser").accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(data), SignupFormData.class).exchange().expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON).expectBody(String.class).consumeWith(response -> {
                    String contentString = response.getResponseBody().toString();
                    try {
                        TokenData tokenData = new ObjectMapper().readValue(contentString, TokenData.class);
                        Files.writeString(Path.of("src/test/resources/jwtToken"), tokenData.getJwtToken(),
                                StandardCharsets.UTF_8, StandardOpenOption.WRITE);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

    }

    @Test
    public void atest() throws Exception {
        SignupFormData data = new SignupFormData("admin", "emai13", "password1", 50, 150);
        webTestClient.get().uri("/signup/test").accept(MediaType.APPLICATION_JSON).exchange().expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON).expectBody(String.class)
                .consumeWith(response->{
                    String contentString=response.getResponseBody().toString();
                    System.out.println(contentString);
                });
                // .jsonPath("$.email",new StringStartsWith("emai16"));
    }

    @Test
    public void getProfile() throws Exception {
        TokenData tokenData = new TokenData(Files.readString(Path.of("src/test/resources/jwtTokenLogin")), null);
        // TokenData tokenData=new TokenData("empty token",null);
        webTestClient.get().uri("/forms/profile/getUserInfo")
                .header("Authorization", "Bearer " + tokenData.getJwtToken())
                .exchange().expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON).expectBody()
                .jsonPath("$.email", new StringStartsWith("emai21"));
    }
}
