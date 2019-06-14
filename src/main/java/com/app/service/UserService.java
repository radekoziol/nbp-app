package com.app.service;

import com.app.model.user.FacebookUser;
import com.app.model.user.User;
import com.app.repository.UserRepository;
import com.app.repository.UserRequestRepository;
import com.app.service.exceptions.UserAlreadyExistsException;
import com.app.service.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository, UserRequestRepository userRequestRepository) {
        this.userRepository = userRepository;
    }

    public void addUser(User user) {
        checkForDuplicates(user);
        userRepository.save(user);
    }


    private void checkForDuplicates(User user) {
        if (user instanceof FacebookUser) {
            return;
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException("User with given email already exists");
        } else if (userRepository.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistsException("User with given username already exists");
        }
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);

        if (!user.isPresent())
            throw new UserNotFoundException(username);

        return user.get();
    }

}

