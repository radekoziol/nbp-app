package com.app.api.user.facebook;

import lombok.Data;

@Data
public class Profile {

    private transient String username;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}