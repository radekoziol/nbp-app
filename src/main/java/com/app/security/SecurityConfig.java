package com.app.security;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.CompositeFilter;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.List;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String FACEBOOK_LOGIN_URI = "/login/facebook";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.requiresChannel().anyRequest().requiresSecure();
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**", "resources/static/**");
    }

    @Configuration
    @Order(1)
    static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

        private final OAuth2ClientContext oauth2ClientContext;
        private final UserDetailsService userDetailsService;
        private final CustomAuthenticationProvider authProvider;

        @Autowired
        public FormLoginWebSecurityConfigurerAdapter(@Qualifier("oauth2ClientContext") OAuth2ClientContext oauth2ClientContext, @Qualifier("userService") UserDetailsService userDetailsService, CustomAuthenticationProvider authProvider) {
            this.oauth2ClientContext = oauth2ClientContext;
            this.userDetailsService = userDetailsService;
            this.authProvider = authProvider;
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth)
                throws Exception {
            auth.authenticationProvider(authProvider);
            auth.userDetailsService(userDetailsService);
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http
                    .authorizeRequests()
                    .antMatchers("/api/facebookRegister").permitAll()
                    .antMatchers("/api/user/**").permitAll()
                    .antMatchers("/home").fullyAuthenticated()
                    .antMatchers("/api/**").fullyAuthenticated()
                    .and()
                    .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .defaultSuccessUrl("/home", true)
                    .failureForwardUrl("/register")
                    .and()
                    .logout()
                    .logoutSuccessUrl("/register")
                    .and()
                    .addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class)
                    .csrf()
                    .disable();
        }


        @Bean
        public FilterRegistrationBean<OAuth2ClientContextFilter> oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
            FilterRegistrationBean<OAuth2ClientContextFilter> registration = new FilterRegistrationBean<>();
            registration.setFilter(filter);
            registration.setOrder(-100);
            return registration;
        }

        @Bean
        @ConfigurationProperties("facebook")
        public ClientResources facebook() {
            return new ClientResources();
        }

        public Filter ssoFilter() {
            CompositeFilter filter = new CompositeFilter();
            List<Filter> filters = new ArrayList<>();
            filters.add(ssoFilter(facebook(), FACEBOOK_LOGIN_URI));
            filter.setFilters(filters);
            return filter;
        }

        private Filter ssoFilter(ClientResources client, String path) {
            OAuth2ClientAuthenticationProcessingFilter filter = new OAuth2ClientAuthenticationProcessingFilter(
                    path);

            OAuth2RestTemplate template = new OAuth2RestTemplate(client.getClient(), oauth2ClientContext);
            filter.setRestTemplate(template);
            UserInfoTokenServices tokenServices = new UserInfoTokenServices(
                    client.getResource().getUserInfoUri(), client.getClient().getClientId());
            tokenServices.setRestTemplate(template);
            filter.setTokenServices(tokenServices);
            return filter;
        }

    }

    @Configuration
    @EnableOAuth2Sso
    @EnableAuthorizationServer
    @Order(2)
    static class OAuth2SecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                    .antMatchers(FACEBOOK_LOGIN_URI)
                    .authenticated()
                    .and()
                    .csrf().disable();
        }


    }

    @Configuration
    @EnableResourceServer
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
        @Override
        public void configure(HttpSecurity http) throws Exception {
            // @formatter:off
            http.antMatcher("/me").authorizeRequests().anyRequest().authenticated();
            // @formatter:on
        }
    }

}

