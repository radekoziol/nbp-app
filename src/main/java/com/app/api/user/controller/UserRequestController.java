package com.app.api.user.controller;

import com.app.model.user.UserRequest;
import com.app.repository.UserRequestRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/userRequest")
public class UserRequestController {

    private final UserRequestRepository userRequestRepository;

    public UserRequestController(UserRequestRepository userRequestRepository) {
        this.userRequestRepository = userRequestRepository;
    }

    @GetMapping("/byUsername")
    public List<UserRequest> get(String username) {
        return userRequestRepository
                .findAllByUsername(username);
    }

}
