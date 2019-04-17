package com.app.api.user.facebook.api;

import com.app.api.user.facebook.api.interceptors.BearerTokenInterceptor;
import com.app.api.user.facebook.api.interceptors.NoTokenInterceptor;
import com.app.api.user.facebook.api.oauth.FacebookOAuthClient;
import com.app.api.user.facebook.api.oauth.FacebookTokenResponse;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Objects;

public abstract class FacebookApiBinder {

    protected RestTemplate restTemplate;

    public FacebookApiBinder(String authorizationCode) {
        this.restTemplate = new RestTemplate();
        String accessToken = new FacebookOAuthClient().getAccessTokenFromAuthorizationCode(authorizationCode);
        configureRestTemplate(accessToken);
    }

    private void configureRestTemplate(String accessToken){
        if (accessToken != null) {
            this.restTemplate.getInterceptors().add(getBearerTokenInterceptor(accessToken));
        } else {
            this.restTemplate.getInterceptors().add(getNoTokenInterceptor());
        }
    }

    private ClientHttpRequestInterceptor getBearerTokenInterceptor(String accessToken) {
        return new BearerTokenInterceptor(accessToken);
    }

    private ClientHttpRequestInterceptor getNoTokenInterceptor() {
        return new NoTokenInterceptor();
    }

}
