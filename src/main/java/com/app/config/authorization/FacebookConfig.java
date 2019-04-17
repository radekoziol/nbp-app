//package com.app.config.authorization;
//
//import com.app.api.user.facebook.FacebookClient;
//import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AnonymousAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
//import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
//import org.springframework.security.oauth2.client.authentication.OAuth2LoginAuthenticationToken;
//import org.springframework.security.oauth2.core.OAuth2AccessToken;
//import org.springframework.web.context.annotation.RequestScope;
//
//import static com.mysql.cj.conf.PropertyKey.logger;
//
//@Configuration
//@EnableOAuth2Sso
//public class FacebookConfig {
//
//    private final String CLIENT_REGISTER_ID = "facebook";
//
//    @Bean
//    @RequestScope
//    public FacebookClient facebookClient(OAuth2AuthorizedClientService clientService) {
//
//        AnonymousAuthenticationToken authentication = (AnonymousAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
//        String accessToken = getAccessToken(authentication, clientService);
//
//        return new FacebookClient(accessToken);
//    }
//
//
//    private String getAccessToken(Authentication authentication, OAuth2AuthorizedClientService clientService) {
//
//        if (authentication.getClass().isAssignableFrom(OAuth2AuthenticationToken.class)) {
//            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
//            String clientRegistrationId = oauthToken.getAuthorizedClientRegistrationId();
//            if (clientRegistrationId.equals(CLIENT_REGISTER_ID)) {
//                OAuth2AuthorizedClient client = clientService.loadAuthorizedClient(clientRegistrationId, oauthToken.getName());
//                return client.getAccessToken().getTokenValue();
//            }
//        }
//        return null;
//    }
//
//}
