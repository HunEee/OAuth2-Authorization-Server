package com.spring_security.zoauth2_server.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring_security.zoauth2_server.entity.RegisterEntity;

public interface RegisterRepository extends JpaRepository<RegisterEntity, String> {
	
    Optional<RegisterEntity> findByClientId(String clientId);

}