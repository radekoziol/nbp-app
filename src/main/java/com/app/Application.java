package com.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;




/*
    TODO adding simple html for any request ..
    https://stackoverflow.com/questions/1483063/how-to-handle-static-content-in-spring-mvc
    https://stackoverflow.com/questions/15479213/how-to-serve-html-files-with-spring
 */
@SpringBootApplication

//@ComponentScan({"com.app"})
//@EntityScan("com.app.model")
@EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration.class})
//@EnableJpaRepositories("com.app.repository")
public class Application extends SpringBootServletInitializer{

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}