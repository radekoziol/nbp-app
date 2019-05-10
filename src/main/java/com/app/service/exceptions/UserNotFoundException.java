package com.app.service.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.function.Supplier;

@Getter
@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Person could not be found")  // 404
public class UserNotFoundException extends RuntimeException {

    private Long id;
    private String username;

    public UserNotFoundException(final long id) {
        super("Person could not be found with id: " + id);
        this.id = id;
    }

    public UserNotFoundException(String username) {
        super("Person could not be found with username: " + username);
        this.username = username;
    }

}
