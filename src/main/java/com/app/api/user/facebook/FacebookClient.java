package com.app.api.user.facebook;

import com.app.api.user.facebook.api.FacebookApiBinder;
import com.app.model.user.User;

public class FacebookClient extends FacebookApiBinder {

    private static final String GRAPH_API_BASE_URI = "https://graph.facebook.com/v3.2";

    public FacebookClient(String authorizationCode) {
        super(authorizationCode);
    }

    public User createUserFromFacebookProfile() {
        Profile profile = askForUserProfile();
        return createUser(profile);
    }

    private User createUser(Profile profile) {
        return new User(profile.getUsername(), profile.getEmail(), User.getRandomPassword());
    }

    private Profile askForUserProfile() {
        return restTemplate.getForObject(GRAPH_API_BASE_URI + "/me", Profile.class);
    }


}
