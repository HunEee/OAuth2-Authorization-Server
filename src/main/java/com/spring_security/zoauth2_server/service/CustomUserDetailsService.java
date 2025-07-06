package com.spring_security.zoauth2_server.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.spring_security.zoauth2_server.entity.UserEntity;
import com.spring_security.zoauth2_server.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
    	System.out.println("CustomUserDetailsService -> 생성자 호출 ");
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	System.out.println("CustomUserDetailsService -> loadUserByUsername : " + username);
    	
        UserEntity entity = userRepository.findByUsername(username).orElseThrow();

        UserDetails userDetails = User.builder() //jackson2 모듈에 의한 역직렬화 문제 해결을 위한 UserDetails타입의 User.builder
                .username(entity.getUsername())
                .password(entity.getPassword())
                .roles(entity.getRole())
                .build();

        return userDetails;
    }
}
