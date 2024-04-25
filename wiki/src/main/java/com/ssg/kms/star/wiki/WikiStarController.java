package com.ssg.kms.star.wiki;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
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
@RequestMapping("/api/wikistar")
public class WikiStarController {
	private final WikiStarService wikiStarService;
	
	@PostMapping("/check/{wikiId}/{userId}")
	public ResponseEntity checkWikiStar(@PathVariable Long wikiId, @PathVariable Long userId) {
		wikiStarService.checkWiki(wikiId, userId);
		
		return ResponseEntity.ok(wikiStarService.readStar(wikiId));
	}
	
	@GetMapping("/read/{wikiId}")
    public ResponseEntity readWikiStar(@PathVariable Long wikiId) {
		return ResponseEntity.ok(wikiStarService.readStar(wikiId));
    }
	
	@GetMapping("/read/my/{userId}")
    public ResponseEntity<List<GetWikiMapping>> readMyWikiStar(@PathVariable Long userId) {
		return ResponseEntity.ok(wikiStarService.readMyStar(userId));
    }
	
	@GetMapping("/ischeck/{wikiId}/{userId}")
    public ResponseEntity isCheck(@PathVariable Long wikiId, @PathVariable Long userId) {
		return ResponseEntity.ok(wikiStarService.isCheck(wikiId, userId));
    }
	
	////////////////////
	
	@GetMapping("/readStarPersonal/{wikiId}/{userId}")
	public ResponseEntity readStarPersonal(@PathVariable Long wikiId, @PathVariable Long userId) {
		return ResponseEntity.ok(wikiStarService.readStarPersonal(wikiId, userId));
	}
	
	// 利먭꺼李얘린 �닚�쐞
	@GetMapping("/topCounts")
	public ResponseEntity<List<WikiStarDTO>> getTop5WikiStarCounts() {
        return ResponseEntity.ok(wikiStarService.findTop5WikiStarCounts());
    }

	
}
