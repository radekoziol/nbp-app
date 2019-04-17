package com.app.repository;

import com.app.model.user.User;
import com.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    private UserService userService;

    @Autowired
    public DataLoader(UserService userService) {
        this.userService = userService;
    }

    public void run(ApplicationArguments args) {

        User user = new User("admin", "notadmin@adm.pl", "{noop}admin123");
        user.setRole("ADMIN");

        userService.addUser(user);
    }

}