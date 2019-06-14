package com.app.security;

import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;

class ClientResources {

    private static final String REDIRECT_URI = "https://ec2-34-227-158-110.compute-1.amazonaws.com/api/facebookRegister";

    @NestedConfigurationProperty
    private AuthorizationCodeResourceDetails client = new AuthorizationCodeResourceDetails();

    @NestedConfigurationProperty
    private ResourceServerProperties resource = new ResourceServerProperties();

    public ClientResources() {
        client.setUseCurrentUri(false);
        client.setPreEstablishedRedirectUri(REDIRECT_URI);
    }

    public AuthorizationCodeResourceDetails getClient() {
        return client;
    }

    public ResourceServerProperties getResource() {
        return resource;
    }
}
