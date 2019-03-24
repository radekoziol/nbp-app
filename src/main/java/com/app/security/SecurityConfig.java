package com.app.security;

import com.app.api.user.exceptions.UserNotFoundException;
import com.app.model.user.User;
import com.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserRepository userRepository;

    @Autowired
    public SecurityConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .antMatchers( "/home", "/register").authenticated()
                .and()
                .formLogin()
                .defaultSuccessUrl("/", true)
//                .failureForwardUrl("/register")
                .permitAll()
                .and()
                .csrf()
                .disable()
                .httpBasic();

    }

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
            auth
                    .userDetailsService(username -> userRepository
                            .findByUsername(username)
                            .orElse(User.getUnauthorizedUser())
                    );
    }


}
