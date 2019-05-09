package com.app.api.user.controller;

import com.app.api.user.facebook.FacebookClient;
import com.app.api.user.resource.UserResource;
import com.app.model.user.User;
import com.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.net.ssl.*;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

@RestController
public class FacebookUserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public FacebookUserController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @CrossOrigin
    @GetMapping(path = "/api/facebookRegister")
    public RedirectView register(@RequestParam("code") String authorizationCode, HttpServletRequest request) {

        FacebookClient facebookClient = new FacebookClient(authorizationCode);
        User user = facebookClient.createUserFromFacebookProfile();

        userService.addUser(user);
        authenticateUserAndSetSession(user, request);

        return new RedirectView("https://localhost/home");
    }


    private void authenticateUserAndSetSession(User user, HttpServletRequest request) {

        String username = user.getUsername();
        String password = user.getPassword();
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

        // generate session if one doesn't exist
        request.getSession();

        token.setDetails(new WebAuthenticationDetails(request));
        Authentication authenticatedUser = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
    }


}
