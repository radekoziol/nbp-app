package com.app.api.user.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Person could not be found with given id")  // 404
public class UserNotFoundException extends RuntimeException {

    private final Long id;

    public UserNotFoundException(final long id) {
        super("Person could not be found with id: " + id);
        this.id = id;
    }

}
