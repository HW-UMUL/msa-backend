package com.ssg.kms.log;

import java.util.List;
import java.util.Set;

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
@RequestMapping("/api/wikilog")
public class WikiLogController {
	private final WikiLogService wikiLogService;
		
	@GetMapping("/read/{wikiLogId}/{userId}")
    public ResponseEntity<WikiLog> readWikiLog(@PathVariable Long wikiLogId, @PathVariable Long userId) {
        return ResponseEntity.ok(wikiLogService.readWikiLog(wikiLogId, userId));
    }
	
	@GetMapping("/readall/{wikiId}/{userId}")
    public ResponseEntity<Set<WikiLog>> readWikiLogByWikiId(@PathVariable Long wikiId, @PathVariable Long userId) {
        return ResponseEntity.ok(wikiLogService.readWikiLogByWikiId(wikiId, userId));
    }
		
	@DeleteMapping("/delete/{wikiLogId}/{userId}")
    public ResponseEntity deleteWikiLog(@PathVariable Long wikiLogId, @PathVariable Long userId) {
		wikiLogService.deleteWikiLog(wikiLogId, userId);
        return ResponseEntity.ok(null);
    }
	
	// 理쒖떊�닚
	@GetMapping("/recent")
	public List<WikiLogDTO> getLatestLogsPerWiki() {
        return wikiLogService.findLatestLogsPerWiki();
    }	
}
