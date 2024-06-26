package com.ssg.kms.user;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssg.kms.FileManager.FileManager;
import com.ssg.kms.auth.AuthService;

import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@CrossOrigin(origins="*", exposedHeaders="Authorization")
@RestController
//@CrossOrigin(origins="*")
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
	private final UserService userService;
	private final AuthService authService;
	
	@PostMapping("/signup")
	public ResponseEntity<User> signup(@Valid @RequestBody UserDto userDto) {
		return ResponseEntity.ok(userService.signup(userDto));
	}
	
	/// find email
	///////////////////////////
	
	@GetMapping("/search/email/{searchKeyword}")
	public ResponseEntity<List<String>> searchEmails(@PathVariable String searchKeyword) {
		return ResponseEntity.ok(userService.searchEmails(searchKeyword, userService.getMyUserWithAuthorities()));
	}	
	
	///////////////////////////
	/// update info
	@PutMapping("/update/username")
	public ResponseEntity<String> updateUsername(@Valid @RequestBody UsernameDTO usernameDto) {
		return ResponseEntity.ok(userService.updateUsername(usernameDto, userService.getMyUserWithAuthorities()));
	}
	
	@PutMapping("/update/email")
	public ResponseEntity<String> updateEmail(@Valid @RequestBody EmailDTO emailDto) {
		return ResponseEntity.ok(userService.updateEmail(emailDto, userService.getMyUserWithAuthorities()));
	}
	
	@PutMapping("/update/password")
	public ResponseEntity<Boolean> updatePassword(@Valid @RequestBody PasswordDTO passwordDto) {
		return ResponseEntity.ok(userService.updatePassword(passwordDto, userService.getMyUserWithAuthorities()));
	}
	
	@PutMapping("/update/image")
	public ResponseEntity<Boolean> updateImage(@RequestPart(value = "files", required = false) MultipartFile file) {
		return ResponseEntity.ok(userService.updateImage(file, userService.getMyUserWithAuthorities()));
	}
	

	
	///////////////////////////////
	
	@GetMapping("/getinfo")
	public ResponseEntity<List<String>> getInfo() {
		return ResponseEntity.ok(userService.getInfo(userService.getMyUserWithAuthorities()));
	}	
	
	
	@GetMapping("/getuserid")
	public ResponseEntity<Long> getUserId() {
		return ResponseEntity.ok(userService.getUserId(userService.getMyUserWithAuthorities()));
	}	

	@GetMapping("/whoAreYou/{userId}")
	public ResponseEntity whoami(@PathVariable Long userId) {
		return ResponseEntity.ok(userService.whoAreYou(userId, userService.getMyUserWithAuthorities()));
	}	
	
	@PostMapping("/whoAreYou/email")
	public ResponseEntity whoami(@RequestBody EmailDTO emailDto) {
		return ResponseEntity.ok(userService.whoAreYouEmail(emailDto, userService.getMyUserWithAuthorities()));
	}	

	
	@PostMapping("/login")
	public ResponseEntity<String> login(@Valid @RequestBody LoginDto loginDto) {
		
		String jwtToken = authService.authorize(loginDto);
        // 서버에서 쿠키 설정
//        Cookie cookie = new Cookie("jwtToken", jwtToken.substring(7));
//        cookie.setMaxAge(60 * 60 * 24); // 쿠키의 유효기간 설정
//        cookie.setPath("/");
//        response.addCookie(cookie);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Set-Cookie", "jwtToken=" + jwtToken.substring(7) + "; Path=/; Max-Age=3600"); //3600초

		return ResponseEntity.ok().headers(headers).body(loginDto.getUsername());
	}


	@DeleteMapping("/delete")
	public ResponseEntity<Boolean> test() {
		return ResponseEntity.ok(userService.delete(userService.getMyUserWithAuthorities()));
	}
	
	@GetMapping("/user")
	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
	public ResponseEntity<User> getMyUserInfo() {
		return ResponseEntity.ok(userService.getMyUserWithAuthorities().get());
	}

	@GetMapping("/user/{username}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	public ResponseEntity<User> getUserInfo(@PathVariable String username) {
		return ResponseEntity.ok(userService.getUserWithAuthorities(username).get());
	}
	
	// create admin user 
	@PostConstruct
    public void createAdminUser() {
        userService.createAdminUser();
    }
	
	// get isAdmin
	@GetMapping("/user/isadmin")
	public ResponseEntity<Boolean> isAdmin() {
	    Optional<User> user = userService.getMyUserWithAuthorities();
	    boolean isAdmin = userService.isAdmin(user);
	    return ResponseEntity.ok(isAdmin);
	}


	// get all User
	@GetMapping("/user/allusers")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	public ResponseEntity<List<User>> getAllUserInfo() {
		return ResponseEntity.ok(userService.getAllUserinfo());
	}
	
	// get all User Role
	@GetMapping("/user/userrole/{userid}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	public ResponseEntity<List<UserRole>> getUserRole(@PathVariable Long userid) {
		return ResponseEntity.ok(userService.getUserRoleByUserId(userid));
	}
	
	// get UserRole Info
	@GetMapping("/user/role/{userid}")
	public ResponseEntity<List<UserRoleDTO>> getUserRoleInfo(@PathVariable Long userid) {
		return ResponseEntity.ok(userService.getRoleByUserId(userid));
	}
	
	// delete user
	@DeleteMapping("/delete/{userid}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	public ResponseEntity<Boolean> deleteUser(@PathVariable Long userid) {
		return ResponseEntity.ok(userService.deleteUser(userid));
	}
	
////////////////////// 다른 사람 페이지 필요해서
	////////////////// 나중에 상태메세지나 이메일 아이디 정도 가져오면 좋을 듯 싶음
	@GetMapping("/read/user/{userId}")
	public ResponseEntity<User> getOtherUserInfo(@PathVariable Long userId) {
		return ResponseEntity.ok(userService.getOtherUserInfo(userId, userService.getMyUserWithAuthorities()));
	}
}
