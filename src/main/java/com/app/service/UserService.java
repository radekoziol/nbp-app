package com.app.service;

import com.app.exceptions.UserAlreadyExistsException;
import com.app.model.user.User;
import com.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addUser(User user) {
        checkForDuplicates(user);

        userRepository.save(user);
    }


    private void checkForDuplicates(User user) {
        if(userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException("User with given email already exists");
        } else if (userRepository.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistsException("User with given username already exists");
        }
    }


}

