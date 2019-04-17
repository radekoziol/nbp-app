package com.app.api.user.facebook;

import com.app.api.user.facebook.interceptors.BearerTokenInterceptor;
import com.app.api.user.facebook.interceptors.NoTokenInterceptor;
import com.app.api.user.facebook.oauth.FacebookOAuthClient;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

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
