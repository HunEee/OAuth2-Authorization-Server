package com.spring_security.zoauth2_server.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;

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
    
    
    
}
