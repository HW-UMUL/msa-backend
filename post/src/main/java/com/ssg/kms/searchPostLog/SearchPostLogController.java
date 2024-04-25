package com.ssg.kms.searchPostLog;

import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins="*", exposedHeaders="Authorization")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post/searchlog")
public class SearchPostLogController {
	private final SearchPostLogService searchLogService;

	@PostMapping("/save/{userId}")
	public ResponseEntity<SearchPostLog> saveSearchLog(@RequestBody SearchPostLog searchLog, @PathVariable Long userId){
		return ResponseEntity.ok(searchLogService.saveSearchLog(searchLog, userId));
	}
	
	@GetMapping("/read/{userId}")
	public ResponseEntity<Set<SearchPostLog>> readSearchLog(@PathVariable Long userId){
		return ResponseEntity.ok(searchLogService.readSearchLog(userId));
	}
	 
	@PostMapping("/delete/{userId}")
	public ResponseEntity<SearchPostLog> deleteSearchLog(@RequestBody SearchPostLog searchLog, @PathVariable Long userId){
		return ResponseEntity.ok(searchLogService.deleteSearchLog(searchLog.getPostSearchLogContent(), userId));	
	} 
	
//	@DeleteMapping("/delete/{content}")
//	public ResponseEntity<SearchLog> deleteSearchLog(@PathVariable String content){
//		return ResponseEntity.ok(searchLogService.deleteSearchLog(content, userService.getMyUserWithAuthorities()));	
//	}

	
}
