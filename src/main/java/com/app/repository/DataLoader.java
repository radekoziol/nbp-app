package com.app.repository;

import com.app.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    private UserRepository userRepository;

    @Autowired
    public DataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void run(ApplicationArguments args) {

        User user = new User("user", "admin@adm.pl", "{noop}admin123");
        user.setRole("USER");

        User user2 = new User("admin", "notadmin@adm.pl", "{noop}notadmin123");
        user.setRole("ADMIN");

        userRepository.save(user);
        userRepository.save(user2);
    }

}