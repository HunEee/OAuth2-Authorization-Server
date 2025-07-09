package com.spring_security.zoauth2_server.config;

import static org.springframework.security.config.Customizer.withDefaults;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
    	System.out.println("***SecurityConfig bCryptPasswordEncoder***");
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	System.out.println("***SecurityConfig SecurityFilterChain***");

        http.csrf((csrf) -> csrf.disable())
        	.authorizeHttpRequests((auth) -> auth.anyRequest().permitAll())
        	.formLogin(withDefaults());
        
        
        return http.build();
    }
    
    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
    	System.out.println("***SecurityConfig AuthorizationServerSettings***");
        return AuthorizationServerSettings.builder().build();
    }
    
    
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)//여러개의 필터가 있을 경우 우선순위를 가장 높게 설정
    public SecurityFilterChain authorizationServer(HttpSecurity http) throws Exception {
    	System.out.println("***SecurityConfig authorizationServer***");
//        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
//        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class).oidc(withDefaults());
        
    	OAuth2AuthorizationServerConfigurer authorizationServerConfigurer =
    			http.getConfigurer(OAuth2AuthorizationServerConfigurer.class);
		authorizationServerConfigurer.oidc(Customizer.withDefaults());

        http.exceptionHandling(
        		(exceptions) -> exceptions.defaultAuthenticationEntryPointFor(
	                                new LoginUrlAuthenticationEntryPoint("/login"),
	                                new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
        						)
        );

        return http.build();
    }
    
    @Bean
    public JWKSource<com.nimbusds.jose.proc.SecurityContext> jwkSource() {
        KeyPair keyPair = generateRsaKey();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAKey rsaKey = new RSAKey.Builder(publicKey).privateKey(privateKey).keyID(UUID.randomUUID().toString()).build();
        JWKSet jwkSet = new JWKSet(rsaKey);

        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }

    private static KeyPair generateRsaKey() {
        KeyPair keyPair;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        return keyPair;
    }
    
    
    
}
