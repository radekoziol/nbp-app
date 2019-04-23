package com.app.api.user.facebook;

import com.app.model.user.FacebookUser;
import com.app.model.user.User;

import java.util.Objects;

public class FacebookClient extends FacebookApiBinder {

    private static final String GRAPH_API_BASE_URI = "https://graph.facebook.com/v3.2";

    public FacebookClient(String authorizationCode) {
        super(authorizationCode);
    }

    public User createUserFromFacebookProfile() {
        long id = askForUserId();
        return createUser(id);
    }

    private User createUser(long id) {
        return new FacebookUser("Facebook", id);
    }

    private long askForUserId() {
        Profile profile = restTemplate.getForObject(GRAPH_API_BASE_URI + "/me", Profile.class);
        return Long.parseLong(Objects.requireNonNull(profile).getId());
    }

}
