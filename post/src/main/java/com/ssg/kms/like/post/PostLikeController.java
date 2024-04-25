package com.ssg.kms.like.post;

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
@RequestMapping("/api/postlike")
public class PostLikeController {
	private final PostLikeService postLikeService;
	
	@PostMapping("/check/{postId}/{userId}")
	public ResponseEntity checkPostLike(@PathVariable Long postId, @PathVariable Long userId) {
		postLikeService.checkPost(postId, userId);
		
		return ResponseEntity.ok(postLikeService.readLike(postId));
	}
	
	@GetMapping("/read/{postId}")
    public ResponseEntity readPostLike(@PathVariable Long postId) {
		return ResponseEntity.ok(postLikeService.readLike(postId));
    }
	
	@GetMapping("/read/my/{userId}")
    public ResponseEntity<List<GetPostMapping>> readMyPostLike(@PathVariable Long userId) {
		return ResponseEntity.ok(postLikeService.readMyLike(userId));
    }
	
	@GetMapping("/ischeck/{postId}/{userId}")
    public ResponseEntity isCheck(@PathVariable Long postId, @PathVariable Long userId) {
		return ResponseEntity.ok(postLikeService.isCheck(postId, userId));
    }

	////////////////////////
	@GetMapping("/readLikePersonal/{postId}/{userId}")
	public ResponseEntity readLikePersonal(@PathVariable Long postId, @PathVariable Long userId) {
		return ResponseEntity.ok(postLikeService.readLikePersonal(postId, userId));
	}
	
	@GetMapping("/topCounts")
    public ResponseEntity<List<PostLikeDTO>> getTop5WikiLikeCounts() {
        return ResponseEntity.ok(postLikeService.findTop5PostLikeCounts());
    }

}
