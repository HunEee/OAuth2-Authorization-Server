package com.spring_security.zoauth2_server.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring_security.zoauth2_server.dto.UserDTO;
import com.spring_security.zoauth2_server.entity.UserEntity;
import com.spring_security.zoauth2_server.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
    	System.out.println("UserService -> 생성자 호출 ");
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void join(UserDTO dto) {
    	System.out.println("UserService -> join :"+dto);
    	
        UserEntity entity = new UserEntity();
        entity.setUsername(dto.getUsername());
        entity.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        entity.setNickname(dto.getUsername());
        entity.setPhone(dto.getPhone());
        entity.setRole("ADMIN");

        userRepository.save(entity);
    }
}
