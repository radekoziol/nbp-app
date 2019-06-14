package com.app.api.user.facebook.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Objects;

@Component
public class FacebookOAuthClient {

    private static final String GRANT_TYPE = "authorization_code";
    private static final String GRAPH_API_BASE_URI = "https://graph.facebook.com/v3.2/oauth/access_token";
    private static final String REDIRECT_URI = "https://ec2-34-227-158-110.compute-1.amazonaws.com/api/facebookRegister";
    private static String clientId;
    private static String clientSecret;
    private RestTemplate restTemplate = new RestTemplate();


    @Autowired
    public FacebookOAuthClient(@Value("${spring.security.oauth2.client.registration.facebook.client-id}") String clientId,
                               @Value("${spring.security.oauth2.client.registration.facebook.client-secret}") String clientSecret) {
        FacebookOAuthClient.clientId = clientId;
        FacebookOAuthClient.clientSecret = clientSecret;
    }

    public FacebookOAuthClient() {

    }


    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    public String getAccessTokenFromAuthorizationCode(String authorizationCode) {
        HttpHeaders headers = createHeaders();
        String uri = buildUri(authorizationCode);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<FacebookTokenResponse> response = null;
        try {
            response = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    entity,
                    FacebookTokenResponse.class);
        } catch (HttpClientErrorException e) {
            System.out.println(uri);
            if (response != null) {
                System.out.println(response.getBody());
            }
        }

        if (response != null) {
            System.out.println(response.getBody());
        }

        return Objects.requireNonNull(response.getBody()).access_token;
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        return headers;
    }

    private String buildUri(String authorizationCode) {
        return UriComponentsBuilder.fromHttpUrl(GRAPH_API_BASE_URI)
                .queryParam("code", authorizationCode)
                .queryParam("client_id", clientId)
                .queryParam("client_secret", clientSecret)
                .queryParam("redirect_uri", REDIRECT_URI)
                .queryParam("grant_type", GRANT_TYPE)
                .toUriString();
    }

}
