package com.app.api.user.request;

import com.app.model.user.User;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserRegisterRequest {

    @NotNull
    @Size(min = 1, max =    20)
    private String username;

    @NotNull
    @Size(min = 8)
    private String password;

    @NotNull
    @Pattern(regexp = User.emailRegex)
    private String email;

    public UserRegisterRequest() {
    }

    public UserRegisterRequest(@NotNull @Size(min = 1, max = 20) String username, @NotNull @Size(min = 8) String password, @NotNull @Pattern(regexp = User.emailRegex) String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public User extractUser() {
        return new User(username, email, password);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
