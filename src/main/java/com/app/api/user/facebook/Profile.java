package com.app.api.user.facebook;

import lombok.Data;

@Data
public class Profile {

    private String name;
    private String email;

    public String getUsername() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}