package com.spring_security.zoauth2_server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

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
}
