package com.ssg.kms.searchWikiLog;

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
@RequestMapping("/api/wiki/searchlog")
public class SearchWikiLogController {
	private final SearchWikiLogService searchWikiLogService;

	@PostMapping("/save/{userId}")
	public ResponseEntity<SearchWikiLog> saveSearchWikiLog(@RequestBody SearchWikiLog searchWikiLog, @PathVariable Long userId){
		return ResponseEntity.ok(searchWikiLogService.saveSearchWikiLog(searchWikiLog, userId));
	}
	
	@GetMapping("/read/{userId}")
	public ResponseEntity<Set<SearchWikiLog>> readSearchWikiLog(@PathVariable Long userId){
		return ResponseEntity.ok(searchWikiLogService.readSearchWikiLog(userId));
	}
	 
	@PostMapping("/delete/{userId}")
	public ResponseEntity<SearchWikiLog> deleteSearchWikiLog(@RequestBody SearchWikiLog searchWikiLog, @PathVariable Long userId){
		return ResponseEntity.ok(searchWikiLogService.deleteSearchWikiLog(searchWikiLog.getWikiSearchLogContent(), userId));	
	}
	
//	@DeleteMapping("/delete/{content}")
//	public ResponseEntity<SearchLog> deleteSearchLog(@PathVariable String content){
//		return ResponseEntity.ok(searchLogService.deleteSearchLog(content, userService.getMyUserWithAuthorities()));	
//	}

	
}
