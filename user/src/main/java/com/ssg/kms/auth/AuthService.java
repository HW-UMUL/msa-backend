package com.ssg.kms.auth;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ssg.kms.security.JwtUtil;
import com.ssg.kms.security.TokenProvider;
import com.ssg.kms.user.LoginDto;
import com.ssg.kms.user.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	
	private final JwtUtil jwtUtil;
	private final UserService userService;
    private final TokenProvider tokenProvider;
    
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final RedisTemplate<String, String> redisTemplate;

	@Value("${jwt.jwtSecret}") //application.properties�뿉 ���옣�릺�뼱 �엳�뒗 媛믪쓣 媛��졇�삩�떎.
    private String jwtSecret;
    @Value("${jwt.expiredMs}") //application.properties�뿉 ���옣�릺�뼱 �엳�뒗 媛믪쓣 媛��졇�삩�떎.
    private Long expiredMs;

	

//    private final TokenProvider tokenProvider;
//    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    
    public String authorize(LoginDto loginDto) {


        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        // authenticate 메소드가 실행이 될 때 CustomUserDetailsService class의 loadUserByUsername 메소드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 해당 객체를 SecurityContextHolder에 저장하고
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // authentication 객체를 createToken 메소드를 통해서 JWT Token을 생성
        String jwt = tokenProvider.createToken(authentication);

        return "Bearer " + jwt;
    }
    
    public boolean isTokenExpired(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(jwtSecret).build().parseClaimsJws(token).getBody();
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (ExpiredJwtException ex) {
            return true; // �넗�겙�씠 留뚮즺�릺�뿀�쓬�쓣 �굹���깂
        }
    }
}
