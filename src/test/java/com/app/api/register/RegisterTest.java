package com.app.api.register;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.thymeleaf.util.StringUtils;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class RegisterTest {

    @Autowired
    private MockMvc mockMvc;


/*
    (min = 1, max = 20)
    username;
    (min = 8)
    password;
    (regexp = emailRegex)
    email;
*/

    @Test
    public void testPasswordToShort() throws Exception {
        RequestBuilder requestBuilder = post("/user")
                .param("username", "admin222")
                .param("email", "admin123@a.a")
                .param("password", "7777777");
                // len = 7.a"
        mockMvc.perform(requestBuilder)
                .andExpect(status().is4xxClientError());
    }


    @Test
    public void testNullUsername() throws Exception {
        RequestBuilder requestBuilder = post("/user")
                .param("username", "")
                .param("email", "admin123@a.a")
                .param("password", "admin12223");
        mockMvc.perform(requestBuilder)
                .andExpect(status().is4xxClientError());
    }


    @Test
    public void testUsernameToLong() throws Exception {
        RequestBuilder requestBuilder = post("/user")
                .param("username", StringUtils.repeat("a", 21))
                // len = 21
                .param("email", "admin123@a.a")
                .param("password", "admin12223");
        mockMvc.perform(requestBuilder)
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testEmail() throws Exception {
        RequestBuilder requestBuilder = post("/user")
                .param("username", "admin222")
                .param("email", "admin123asdasa.a")
                .param("password", "admin12223");
        mockMvc.perform(requestBuilder)
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testNullEmail() throws Exception {
        RequestBuilder requestBuilder = post("/user")
                .param("username", "admin222")
                .param("email", "")
                .param("password", "admin12223");
        mockMvc.perform(requestBuilder)
                .andExpect(status().is4xxClientError());
    }



}
