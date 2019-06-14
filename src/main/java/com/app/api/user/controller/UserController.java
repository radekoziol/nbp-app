package com.app.api.user.controller;

import com.app.api.user.request.UserRegisterRequest;
import com.app.api.user.resource.UserResource;
import com.app.model.user.User;
import com.app.repository.UserRepository;
import com.app.service.UserService;
import com.app.service.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    private UserService userService;
    private UserRepository userRepository;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public @ResponseBody
    ResponseEntity<UserResource> post(@RequestBody @Valid UserRegisterRequest request, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().header("error", bindingResult.getAllErrors().toString()).build();
        }

        final User user = new User(request);
        userService.addUser(user);
        final URI uri =
                MvcUriComponentsBuilder.fromController(getClass())
                        .path("/{id}")
                        .buildAndExpand(user.getId())
                        .toUri();
        return ResponseEntity.created(uri).body(new UserResource(user));
    }


    @GetMapping(path = "/{id}")
    public ResponseEntity<UserResource> get(@PathVariable final long id) {
        return userRepository
                .findById(id)
                .map(p -> ResponseEntity.ok(new UserResource(p)))
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @GetMapping()
    public ResponseEntity<Resources<UserResource>> all() {

        final List<UserResource> collection =
                userRepository.findAll().stream().map(UserResource::new).collect(Collectors.toList());
        final Resources<UserResource> resources = new Resources<>(collection);

        final String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
        resources.add(new Link(uriString, "self"));
        return ResponseEntity.ok(resources);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") final long id) {
        return userRepository
                .findById(id)
                .map(
                        p -> {
                            userRepository.deleteById(id);
                            return ResponseEntity.noContent().build();
                        })
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping("generate")
    public ResponseEntity<User> generate() {

        User user = new User("radekoziol", "example@com.pl", "admin123");
        // Allowing for duplicates
        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.OK)
                .body(user);
    }


}