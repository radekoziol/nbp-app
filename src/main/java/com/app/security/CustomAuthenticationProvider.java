package com.app.security;

import com.app.model.user.User;
import com.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

@Component
public class CustomAuthenticationProvider
        implements AuthenticationProvider {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {

        String name = "";
        String password = "";

        if (!isFacebookUserLogged(authentication)) {
            name = authentication.getName();
            password = authentication.getCredentials().toString();

            Optional<User> user = userRepository.findByUsername(name);
            if (!user.isPresent())
                throw new AuthenticationException("Wrong credentials") {
                };
            if (!user.get().getPassword().equals(password))
                throw new AuthenticationException("Wrong credentials") {
                };

        }

        // use the credentials
        // and authenticate against the third-party system
        return new UsernamePasswordAuthenticationToken(
                name, password, new ArrayList<>());
    }

    private boolean isFacebookUserLogged(Authentication authentication) {
        return authentication.getCredentials() == null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(
                UsernamePasswordAuthenticationToken.class);
    }
}