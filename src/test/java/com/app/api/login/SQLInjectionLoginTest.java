package com.app.api.login;

import com.app.api.SQLInjectionUtils;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class SQLInjectionLoginTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testUsernameSQLInjection() throws Exception {
        IntStream.range(1, 10).forEach(i -> {

            String sqlInjection = SQLInjectionUtils.generateRandomDangerousQuery();

            RequestBuilder requestBuilder = post("/login")
                    .param("username", sqlInjection)
                    .param("password", "admin123");

            try {
                mockMvc.perform(requestBuilder)
                        .andExpect(status().is2xxSuccessful());
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }

    @Test
    public void testPasswordSQLInjection() throws Exception {

        IntStream.range(1, 10).forEach(i -> {

            String sqlInjection = SQLInjectionUtils.generateRandomDangerousQuery();

            RequestBuilder requestBuilder = post("/login")
                    .param("username", "admin")
                    .param("password", sqlInjection);

            try {
                mockMvc.perform(requestBuilder)
                        .andExpect(status().is2xxSuccessful());
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

    }

}
