package com.ssg.kms.like.reply;

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
@RequestMapping("/api/replylike")
public class ReplyLikeController {
	private final ReplyLikeService replyLikeService;
	
	@PostMapping("/check/{replyId}/{userId}")
	public ResponseEntity checkPostLike(@PathVariable Long replyId, @PathVariable Long userId) {

		replyLikeService.checkReply(replyId, userId);
		
		return ResponseEntity.ok(replyLikeService.readLike(replyId));
	}
	
	@GetMapping("/read/{replyId}")
    public ResponseEntity readPostLike(@PathVariable Long replyId) {
		return ResponseEntity.ok(replyLikeService.readLike(replyId));
    }
	
	@GetMapping("/ischeck/{replyId}/{userId}")
    public ResponseEntity isCheck(@PathVariable Long replyId, @PathVariable Long userId) {
		return ResponseEntity.ok(replyLikeService.isCheck(replyId, userId));
    }
	
	// 되려나 모르겠당 안되넹 
//	@GetMapping("/read/my")
//    public ResponseEntity<List<GetPostMapping>> readMyPostLike() {
//		return ResponseEntity.ok(replyLikeService.readMyLike(userService.getMyUserWithAuthorities()));
//    }
}
