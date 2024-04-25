package com.ssg.kms.like.wiki;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssg.kms.mapping.GetWikiMapping;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins="*", exposedHeaders="Authorization")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wikilike")
public class WikiLikeController {
	private final WikiLikeService wikiLikeService;
	
	@PostMapping("/check/{wikiId}/{userId}")
	public ResponseEntity checkwikiLike(@PathVariable Long wikiId, @PathVariable Long userId) {
		wikiLikeService.checkwiki(wikiId, userId);
		
		return ResponseEntity.ok(wikiLikeService.readLike(wikiId));
	}
	
	@GetMapping("/read/{wikiId}")
    public ResponseEntity readwikiLike(@PathVariable Long wikiId) {
		return ResponseEntity.ok(wikiLikeService.readLike(wikiId));
    }
	
	@GetMapping("/read/my/{userId}")
    public ResponseEntity<List<GetWikiMapping>> readMywikiLike(@PathVariable Long userId) {
		return ResponseEntity.ok(wikiLikeService.readMyLike(userId));
    }
	
	@GetMapping("/ischeck/{wikiId}/{userId}")
    public ResponseEntity isCheck(@PathVariable Long wikiId, @PathVariable Long userId) {
		return ResponseEntity.ok(wikiLikeService.isCheck(wikiId, userId));
    }
	///////////////////////
	
	@GetMapping("/readLikePersonal/{wikiId}/{userId}")
	public ResponseEntity readLikePersonal(@PathVariable Long wikiId, @PathVariable Long userId) {
		return ResponseEntity.ok(wikiLikeService.readLikePersonal(wikiId, userId));
	}
	
	// 醫뗭븘�슂 �닚�쐞
	@GetMapping("/topCounts")
    public ResponseEntity<List<WikiLikeDTO>> getTop5WikiLikeCounts() {
        return ResponseEntity.ok(wikiLikeService.findTop5WikiLikeCounts());
    }
}
