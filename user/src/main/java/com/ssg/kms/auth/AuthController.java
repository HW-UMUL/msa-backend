package com.ssg.kms.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssg.kms.security.JwtFilter;
import com.ssg.kms.security.JwtUtil;
import com.ssg.kms.security.TokenDto;
import com.ssg.kms.user.LoginDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins="*", exposedHeaders="Authorization")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {
	
	private final AuthService authService;
	
	@Value("${jwt.jwtSecret}") //application.properties�뿉 ���옣�릺�뼱 �엳�뒗 媛믪쓣 媛��졇�삩�떎.
    private String jwtSecret;
    @Value("${jwt.expiredMs}") //application.properties�뿉 ���옣�릺�뼱 �엳�뒗 媛믪쓣 媛��졇�삩�떎.
    private Long expiredMs;

    

    @PostMapping("/auth")
    public ResponseEntity<TokenDto> authorize(@Valid @RequestBody LoginDto loginDto) {


    	String jwt = authService.authorize(loginDto); //test2
    	HttpHeaders httpHeaders = new HttpHeaders();
    	// response header에 jwt token에 넣어줌
    	httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, jwt);

    	
        return new ResponseEntity<>(null, httpHeaders, HttpStatus.OK);
    }
    
	@GetMapping("/checkToken")
    public ResponseEntity<?> checkToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7); // "Bearer " �떎�쓬�쓽 �넗�겙 遺�遺� 異붿텧
            boolean isExpired = authService.isTokenExpired(token);
            if (isExpired) {
                return ResponseEntity.ok(true); // 留뚮즺 o
            } else {
                return ResponseEntity.ok(false); // 留뚮즺 x
            }
        } else {
            return ResponseEntity.badRequest().body("Invalid or missing Authorization header");
        }
    }
	
	private final JwtUtil jwtUtil;

	@GetMapping("/refresh/{id}")
	public String getRefresh(@PathVariable("id") Long id) {
		System.out.println("id = " + id + id);
		String refreshToken = jwtUtil.createRefreshToken(id);
		return refreshToken;
	}

}
