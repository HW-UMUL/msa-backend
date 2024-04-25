package com.ssg.kms.follow;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins="*", exposedHeaders="Authorization")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/follow")
public class FollowController {

	private final FollowService followService;
	
	@PostMapping("/check/{otherUserId}/{userId}")
	public ResponseEntity checkFollow(@PathVariable Long otherUserId, @PathVariable Long userId) {

		followService.checkFollow(otherUserId, userId);
		
		return ResponseEntity.ok(followService.readFollower(otherUserId, userId));
	}
	
	@GetMapping("/read/follower/{otherUserId}/{userId}")
    public ResponseEntity readFollower(@PathVariable Long otherUserId, @PathVariable Long userId) {
		return ResponseEntity.ok(followService.readFollower(otherUserId, userId));
    }
	
	@GetMapping("/read/followee/{otherUserId}/{userId}")
    public ResponseEntity readFollowee(@PathVariable Long otherUserId, @PathVariable Long userId) {
		return ResponseEntity.ok(followService.readFollowee(otherUserId, userId));
    }
	
	@GetMapping("/read/follower/{userId}")
    public ResponseEntity readMyFollower(@PathVariable Long userId) {
		return ResponseEntity.ok(followService.readMyFollower(userId));
    }
	
	@GetMapping("/read/followee/{userId}")
    public ResponseEntity readMyFollowee(@PathVariable Long userId) {
		return ResponseEntity.ok(followService.readMyFollowee(userId));
    }


}
