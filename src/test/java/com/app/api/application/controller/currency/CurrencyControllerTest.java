package com.app.api.application.controller.currency;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class CurrencyControllerTest {

    private static final String API_PATH = "http://127.0.0.1:8080/api/currency";
    @Rule
    public final ExpectedException exception = ExpectedException.none();
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getCurrencyPrice() throws Exception {

        RequestBuilder requestBuilder = post("/login")
                .param("username", "admin")
                .param("password", "admin123");
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is3xxRedirection());


        requestBuilder = get(API_PATH + "/getCurrencyPrice")
                .param("currency", "euro")
                .param("date", "2019-03-14");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is2xxSuccessful());


    }

}