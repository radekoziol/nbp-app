package com.app.api.user.controller;

import com.app.model.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserController {


        @PostMapping(path = "/users")
    public ResponseEntity<String> register(@Valid @RequestBody String request) {

        User user = new User();
        user.setUsername(request);

        return ResponseEntity.status(HttpStatus.OK)
                .body(user.toString());

    }



}