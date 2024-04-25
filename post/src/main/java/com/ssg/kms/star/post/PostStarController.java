package com.ssg.kms.star.post;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssg.kms.mapping.GetPostMapping;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins="*", exposedHeaders="Authorization")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/poststar")
public class PostStarController {
	private final PostStarService postStarService;
	
	@PostMapping("/check/{postId}/{userId}")
	public ResponseEntity checkPostStar(@PathVariable Long postId, @PathVariable Long userId) {
		postStarService.checkPost(postId, userId);
		
		return ResponseEntity.ok(postStarService.readStar(postId));
	}
	
	@GetMapping("/read/{postId}")
    public ResponseEntity readPostStar(@PathVariable Long postId) {
		return ResponseEntity.ok(postStarService.readStar(postId));
    }
	
	@GetMapping("/read/my/{userId}")
    public ResponseEntity<List<GetPostMapping>> readMyPostStar(@PathVariable Long userId) {
		return ResponseEntity.ok(postStarService.readMyStar(userId));
    }
	
	@GetMapping("/ischeck/{postId}/{userId}")
    public ResponseEntity isCheck(@PathVariable Long postId, @PathVariable Long userId) {
		return ResponseEntity.ok(postStarService.isCheck(postId, userId));
    }

	
	
	// 利먭꺼李얘린 �닚�쐞
	@GetMapping("/topCounts")
	public ResponseEntity<List<PostStarDTO>> getTop5PostStarCounts() {
		return ResponseEntity.ok(postStarService.findTop5PostStarCounts());
	}
}
