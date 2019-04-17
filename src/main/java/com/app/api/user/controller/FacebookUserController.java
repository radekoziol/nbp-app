package com.app.api.user.controller;

import com.app.api.user.facebook.FacebookClient;
import com.app.api.user.resource.UserResource;
import com.app.model.user.User;
import com.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Objects;
import java.util.stream.IntStream;

@RestController
@RequestMapping(value = "/api")
public class FacebookUserController {

    private final UserService userService;

    @Autowired
    public FacebookUserController( UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/facebookRegister")
    public ResponseEntity<UserResource> register(@RequestParam("code") String authorizationCode) {

        FacebookClient facebookClient = new FacebookClient(authorizationCode);
        User user = facebookClient.createUserFromFacebookProfile();

        userService.addUser(user);

        final URI uri;
        if (user != null) {
            uri = MvcUriComponentsBuilder.fromController(getClass())
                    .path("/{id}")
                    .buildAndExpand(user.getId())
                    .toUri();
            return ResponseEntity.created(uri).body(new UserResource(user));
        } else
            return ResponseEntity.badRequest().body(null);

    }



}
