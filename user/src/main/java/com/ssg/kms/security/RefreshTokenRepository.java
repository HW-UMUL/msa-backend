package com.ssg.kms.security;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ch.qos.logback.core.subst.Token;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {
    
}