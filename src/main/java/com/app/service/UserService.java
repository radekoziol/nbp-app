package com.app.service;

import com.app.service.exceptions.UserAlreadyExistsException;
import com.app.service.exceptions.UserNotFoundException;
import com.app.model.user.User;
import com.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService implements UserDetailsService {

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
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException("User with given email already exists");
        } else if (userRepository.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistsException("User with given username already exists");
        }
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Objects.requireNonNull(username);
        User user = userRepository.findByUsername(username).get();

        if (user == null)
            throw new UserNotFoundException(username);

        return user;
    }

}

