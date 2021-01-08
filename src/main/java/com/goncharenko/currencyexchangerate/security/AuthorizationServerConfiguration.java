package com.goncharenko.currencyexchangerate.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
    private final String clientId;
    private final String clientSecret;
    private final String privateKey;
    private final AuthenticationManager authenticationManager;
    private final TokenStore tokenStore;

    @Autowired
    public AuthorizationServerConfiguration(@Value("${spring.security.oauth2.client.clientId}") String clientId,
                                            @Value("${spring.security.oauth2.client.clientSecret}") String clientSecret,
                                            @Value("${spring.security.oauth2.client.privateKey}") String privateKey,
                                            @Qualifier("authenticationManagerBean") AuthenticationManager authenticationManager,
                                            TokenStore tokenStore) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.privateKey = privateKey;
        this.authenticationManager = authenticationManager;
        this.tokenStore = tokenStore;
    }

    @Bean
    public AccessTokenConverter tokenEnhancer() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(privateKey);
        return converter;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        DefaultTokenServices tokenServices = new DefaultTokenServices();

        tokenServices.setSupportRefreshToken(true);
        tokenServices.setTokenStore(tokenStore);
        tokenServices.afterPropertiesSet();
        tokenServices.setAccessTokenValiditySeconds(600);
        tokenServices.setRefreshTokenValiditySeconds(6000);

        endpoints.authenticationManager(authenticationManager)
                .tokenStore(tokenStore)
                .reuseRefreshTokens(true)
                .tokenServices(tokenServices)
                .accessTokenConverter(tokenEnhancer());
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient(clientId)
                .secret(clientSecret)
                .scopes("read", "write")
                .authorizedGrantTypes("password", "refresh_token")
                .accessTokenValiditySeconds(20000)
                .refreshTokenValiditySeconds(200000);

    }
}
