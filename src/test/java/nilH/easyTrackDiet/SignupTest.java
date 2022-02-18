package nilH.easyTrackDiet;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import nilH.easyTrackDiet.dto.SignupFormData;

import static org.mockito.ArgumentMatchers.contains;


import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.SharedHttpSessionConfigurer.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import java.util.HashMap;
import java.util.Map;


@SpringBootTest
@AutoConfigureMockMvc
public class SignupTest {
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext wac;
    @BeforeEach
    void setup(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).apply(sharedHttpSession()).build();
    }
    @Test
    public void signupReturn() throws Exception{
        Map<String, Object> sessionAttr=new HashMap<String, Object>();
        sessionAttr.put("sessionatr", 0);
        SignupFormData data=new SignupFormData("user", "email7", "password1", 50, 150);
        this.mockMvc.perform(post("/signup/newUser").sessionAttrs(sessionAttr).contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(data)))
        .andExpect(redirectedUrl("/"));
        this.mockMvc.perform(get("/")).andExpect(redirectedUrl("/profile/getUserInfo?display+message=sign+up+success"));
        this.mockMvc.perform(get("/profile/getUserInfo"))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.email", Is.is("email7")));
    }
}
