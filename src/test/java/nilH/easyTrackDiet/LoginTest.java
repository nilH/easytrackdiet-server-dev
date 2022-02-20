package nilH.easyTrackDiet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import nilH.easyTrackDiet.dto.SignupFormData;
import nilH.easyTrackDiet.dto.TokenData;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.SharedHttpSessionConfigurer.*;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginTest {
    @Autowired
    private MockMvc mockMvc;
    @Test
    public void loginReturn() throws Exception{
        SignupFormData data=new SignupFormData(null,"email7","password1",0,0);
        ResultActions resultActions=this.mockMvc.perform(get("/login").contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(data)))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andDo(print()).andExpect(status().isOk());
        MvcResult mvcResult=resultActions.andReturn();
        String contentString=mvcResult.getResponse().getContentAsString();
        TokenData tokenData=new ObjectMapper().readValue(contentString, TokenData.class);
        Files.writeString( Path.of("src/test/resources/jwtToken"), tokenData.getJwtToken(), StandardCharsets.UTF_8, StandardOpenOption.WRITE);
    }
}
