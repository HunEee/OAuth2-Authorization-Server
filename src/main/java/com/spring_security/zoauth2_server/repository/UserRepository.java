package com.spring_security.zoauth2_server.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring_security.zoauth2_server.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);
    
}