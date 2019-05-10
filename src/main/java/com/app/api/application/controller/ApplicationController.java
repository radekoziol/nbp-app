package com.app.api.application.controller;

import com.app.model.user.UserRequest;
import com.app.repository.UserRepository;
import com.app.service.UserRequestService;
import com.app.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ApplicationController {

    protected final UserRequestService userRequestService;

    public ApplicationController(UserRequestService userRequestService) {
        this.userRequestService = userRequestService;
    }

    @RequestMapping({"/user", "/me"})
    public Map<String, String> user(Principal principal) {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("name", principal.getName());
        return map;
    }

    @RequestMapping(value = "/register")
    public String register() {
        return "register";
    }

    @RequestMapping(value = "/login")
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/users")
    public String users() {
        return "users";
    }

    protected ResponseEntity<String> getStandardExternalErrorResponse(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Invalid arguments: " + e.getMessage());
    }

    protected ResponseEntity<String> getStandardInternalErrorResponse(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error occurred: " + e.getMessage());
    }

}


