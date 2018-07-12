package com.app.api.user.controller;

import com.app.api.user.request.UserRegisterRequest;
import com.app.model.user.User;
import com.app.repository.UserRepository;
import com.app.service.UserService;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
public class UserController {

    private UserService userService;
    private UserRepository userRepository;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }


    /*
    https://stackoverflow.com/questions/19619088/required-string-parameter-is-not-present-error-in-spring-mvc
    On the server side you expect your request parameters as query strings but on client side you send a json object.
    To bind a json you will need to create a single class holding all your parameters and use the @RequestBody annotation instead of @RequestParam.
     */
    @PostMapping(path = "/users")
    public @ResponseBody
    ResponseEntity<String> register(@RequestBody @JsonFormat UserRegisterRequest request) {

        User user = request.extractUser();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(user.toString());
    }

    @GetMapping(path = "/users")
    public  ResponseEntity<Iterable<User>> getUsers() {

        return ResponseEntity.status(HttpStatus.OK)
                .body(userRepository.findAll());

    }

    @DeleteMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public void delete(Authentication authentication) {
        User user = (User)authentication.getCredentials();

        userRepository.delete(user);
    }

}