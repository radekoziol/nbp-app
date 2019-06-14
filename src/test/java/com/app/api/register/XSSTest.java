package com.app.api.register;

import com.app.api.SQLInjectionUtils;
import com.app.api.XSSUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.stream.IntStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class XSSTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testUsernameXSS() throws Exception {
        IntStream.range(1, 10).forEach(i -> {

            String sqlInjection = XSSUtils.generateRandomDangerousQuery();

            RequestBuilder requestBuilder = post("/user")
                    .param("username", sqlInjection)
                    .param("email", "admin123@a.a")
                    .param("password", "admin12223");

            try {
                mockMvc.perform(requestBuilder)
                        .andExpect(status().is4xxClientError());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    public void testEmailXSS() throws Exception {
        IntStream.range(1, 10).forEach(i -> {

            String sqlInjection = XSSUtils.generateRandomDangerousQuery();

            RequestBuilder requestBuilder = post("/user")
                    .param("username", "admin123")
                    .param("email", sqlInjection)
                    .param("password", "admin12223");

            try {
                mockMvc.perform(requestBuilder)
                        .andExpect(status().is4xxClientError());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    public void testPasswordXSS() throws Exception {
        IntStream.range(1, 10).forEach(i -> {

            String sqlInjection = XSSUtils.generateRandomDangerousQuery();

            RequestBuilder requestBuilder = post("/user")
                    .param("username", "admin123")
                    .param("email", "admin123@a.a")
                    .param("password", sqlInjection);

            try {
                mockMvc.perform(requestBuilder)
                        .andExpect(status().is4xxClientError());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}