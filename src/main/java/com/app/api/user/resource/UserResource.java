package com.app.api.user.resource;

import com.app.api.user.controller.UserController;
import com.app.model.user.User;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Getter
public class UserResource extends ResourceSupport {

    private final User user;

    public UserResource(User user) {
        this.user = user;
        final long id = user.getId();
        add(linkTo(UserController.class).withRel("user"));
        add(linkTo(methodOn(UserController.class).get(id)).withSelfRel());
    }
}
