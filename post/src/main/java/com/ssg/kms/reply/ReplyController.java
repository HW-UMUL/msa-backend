package com.ssg.kms.reply;

import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssg.kms.post.Post;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins="*", exposedHeaders="Authorization")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reply")
public class ReplyController {
	private final ReplyService replyService;
	
	@PostMapping("/create/{postId}/{userId}")
	public ResponseEntity createReply(@Valid @RequestBody ReplyDTO replyDto, @PathVariable Long postId, @PathVariable Long userId) {
		replyService.createReply(replyDto, userId, postId);
		return ResponseEntity.ok(null);
	}	
	
	@GetMapping("/read/{replyId}/{userId}")
    public ResponseEntity<Reply> readReply(@PathVariable Long replyId, @PathVariable Long userId) {
        return ResponseEntity.ok(replyService.readReply(replyId, userId));
    }
	
	@GetMapping("/readpost/{postId}/{userId}")
    public ResponseEntity<List<Reply>> readAllReply(@PathVariable Long postId, @PathVariable Long userId) {
        return ResponseEntity.ok(replyService.readAllReply(postId, userId));
    }
	
	@GetMapping("/read/my/{userId}")
    public ResponseEntity<List<Post>> readMyReply(@PathVariable Long userId) {
        return ResponseEntity.ok(replyService.readMyReply(userId));
    }

	
	@PutMapping("/update/{replyId}/{userId}")
    public ResponseEntity updateReply(@PathVariable Long replyId, @Valid @RequestBody ReplyDTO replyDto, @PathVariable Long userId) {
		replyService.updateReply(replyId, replyDto, userId);
        return ResponseEntity.ok(null);
    }
	
	@DeleteMapping("/delete/{replyId}/{userId}")
    public ResponseEntity deleteReply(@PathVariable Long replyId, @PathVariable Long userId) {
		replyService.deleteReply(replyId, userId);
        return ResponseEntity.ok(null);
    }
}
