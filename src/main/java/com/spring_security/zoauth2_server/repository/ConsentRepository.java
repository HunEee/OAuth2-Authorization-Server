package com.spring_security.zoauth2_server.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring_security.zoauth2_server.entity.ConsentEntity;

public interface ConsentRepository extends JpaRepository<ConsentEntity, String> {

    Optional<ConsentEntity> findByRegisteredClientIdAndPrincipalName(String registeredClientId, String principalName);
    void deleteByRegisteredClientIdAndPrincipalName(String registeredClientId, String principalName);
    
    
}