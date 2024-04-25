package com.ssg.kms.tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins="*", exposedHeaders="Authorization")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tag")
public class TagController {
	
	private final TagService tagService;
	
	@GetMapping("/read/{tagName}")
    public ResponseEntity readPostByTag(@PathVariable String tagName) {
		return ResponseEntity.ok(tagService.readPostByTag(tagName));
    }
}
