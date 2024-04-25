package com.ssg.kms.tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins="*", exposedHeaders="Authorization")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tagpost")
public class TagPostController {
	
	private final TagPostService tagPostService;
	
	@GetMapping("/read/{postId}")
    public ResponseEntity readTagByPostId(@PathVariable Long postId) {
		return ResponseEntity.ok(tagPostService.readTagByPostId(postId));
    }
	
	@DeleteMapping("/delete/{postId}")
    public ResponseEntity deleteTagByPostId(@PathVariable Long postId) {
		return ResponseEntity.ok(tagPostService.deleteByPostId(postId));
    }
}
