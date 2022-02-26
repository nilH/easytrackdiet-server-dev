package nilH.easyTrackDiet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import nilH.easyTrackDiet.dto.SignupFormData;
import nilH.easyTrackDiet.dto.TokenData;
import reactor.core.publisher.Mono;



import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;



@SpringBootTest
@AutoConfigureWebTestClient
public class LoginTest {
    @Autowired
    private WebTestClient webTestClient;
    @Test
    public void loginGetToken() throws Exception{
        SignupFormData data=new SignupFormData(null,"emai22","password1",0,0);
        webTestClient.post().uri("/login").accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(data), SignupFormData.class).exchange().expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON).expectBody(String.class).consumeWith(response -> {
                    String contentString = response.getResponseBody().toString();
                    try {
                        TokenData tokenData = new ObjectMapper().readValue(contentString, TokenData.class);
                        Files.writeString(Path.of("src/test/resources/jwtTokenLogin"), tokenData.getJwtToken(),
                                StandardCharsets.UTF_8, StandardOpenOption.WRITE);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }
}
