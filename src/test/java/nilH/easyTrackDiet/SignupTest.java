package nilH.easyTrackDiet;

import org.hamcrest.core.StringStartsWith;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import nilH.easyTrackDiet.dto.SignupFormData;
import nilH.easyTrackDiet.dto.TokenData;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;


@AutoConfigureMockMvc
@SpringBootTest
public class SignupTest {
    @Autowired
    private MockMvc mockMvc;

  
    @Test
    public void signupReturn() throws Exception{
        Map<String, Object> sessionAttr=new HashMap<String, Object>();
        sessionAttr.put("sessionatr", 0);
        SignupFormData data=new SignupFormData("user", "email8", "password1", 50, 150);
        ResultActions resultActions=this.mockMvc.perform(post("/signup/newUser").sessionAttrs(sessionAttr).contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(data)))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andDo(print()).andExpect(status().isOk());
        MvcResult mvcResult=resultActions.andReturn();
        String contentString=mvcResult.getResponse().getContentAsString();
        TokenData tokenData=new ObjectMapper().readValue(contentString, TokenData.class);
        Files.writeString( Path.of("src/test/resources/jwtToken"), tokenData.getJwtToken(), StandardCharsets.UTF_8, StandardOpenOption.WRITE);
    }
    @Test
    public void atest() throws Exception{
        Files.writeString( Path.of("src/test/resources/jwtToken"), "tokenData.getJwtToken()", StandardCharsets.UTF_8, StandardOpenOption.WRITE);
    }
    @Test
    public void getProfile() throws Exception{
        TokenData tokenData=new TokenData(Files.readString(Path.of("src/test/resources/jwtToken")), null);
        ResultActions resultActions=this.mockMvc.perform(get("/forms/profile/getUserInfo")
        .header("Authorization", "Bearer "+tokenData.getJwtToken()))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.email",new StringStartsWith("email7")));
        MvcResult mvcResult=resultActions.andReturn();
        String contentString=mvcResult.getResponse().getContentAsString();
        System.out.println(contentString);
    }
}
