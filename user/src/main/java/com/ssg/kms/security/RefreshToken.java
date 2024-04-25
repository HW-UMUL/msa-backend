package com.ssg.kms.security;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@RedisHash("token")
@AllArgsConstructor
@ToString
@Data
public class RefreshToken {
    private Long id;
    @Id
    private String refreshToken;
    @TimeToLive
    private Long expiration;
}