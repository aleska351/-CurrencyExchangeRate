package com.goncharenko.currencyexchangerate.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableResourceServer
public class OAuth2Configuration extends ResourceServerConfigurerAdapter {
    private final TokenStore tokenStore;

    public OAuth2Configuration(TokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .requestMatchers()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/*").access("hasAnyAuthority('MANAGER','CONSUMER', 'ADMIN')")
                .antMatchers(HttpMethod.DELETE, "/*").access("hasAnyAuthority('MANAGER','ADMIN')")
                .antMatchers(HttpMethod.PUT, "/*").access("hasAnyAuthority('MANAGER', 'ADMIN')")
                .antMatchers(HttpMethod.POST, "/*").access("hasAnyAuthority('ADMIN')")
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(new OAuth2AccessDeniedHandler());
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.tokenStore(tokenStore);
    }
}
