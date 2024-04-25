package com.ssg.kms.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtUtil {
    private final RefreshTokenRepository tokenRepository;
    @Value("${jwt.jwtSecret}") //application.properties�뿉 ���옣�릺�뼱 �엳�뒗 媛믪쓣 媛��졇�삩�떎.
    private String secretKey;
    @Value("${jwt.expiredMs}") //application.properties�뿉 ���옣�릺�뼱 �엳�뒗 媛믪쓣 媛��졇�삩�떎.
    private Long expiredMs;

    public String createRefreshToken(Long id) {
//
//    	Claims claims = Jwts.claims();
//        claims.put("id", id);

        String token = Jwts.builder()
                .claim("id", id)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
                
        System.out.println("token = " + token);
        RefreshToken token1 = new RefreshToken(id, token, 10L);
        tokenRepository.save(token1);
        return token;
    }
    
    public Date getExpired(String token, String jwtSecret){
    	try {
    		Claims claims = Jwts.parser().setSigningKey(jwtSecret).build().parseClaimsJws(token).getBody();
    		return claims.getExpiration();
    	} catch (Exception e) {
    		e.printStackTrace();
    		return null;
    	}
    }
}
