package com.ssg.kms.wiki;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins="*", exposedHeaders="Authorization")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wiki")
public class WikiController {
	private final WikiService wikiService;
	
	@PostMapping("/create/{userId}")
	public ResponseEntity createWiki(@Valid @RequestBody WikiDTO wikiDto, @PathVariable Long userId) {
		wikiService.createWiki(wikiDto, userId);
		return ResponseEntity.ok(null);
	}
	
	@GetMapping("/read/{wikiId}/{userId}")
    public ResponseEntity<Wiki> readWiki(@PathVariable Long wikiId, @PathVariable Long userId) {
        return ResponseEntity.ok(wikiService.readWiki(wikiId, userId));
    }
	
	@GetMapping("/read/{userId}")
    public ResponseEntity<List<Wiki>> readAllWiki(@PathVariable Long userId) {
        return ResponseEntity.ok(wikiService.readAllWiki(userId));
    }
	
	@GetMapping("/read/my/{userId}")
    public ResponseEntity<List<Wiki>> readMyWiki(@PathVariable Long userId) {
        return ResponseEntity.ok(wikiService.readMyWiki(userId));
    }
	
	@GetMapping("/read/public/{tableId}/{userId}")
    public ResponseEntity<Page<WikiResponseDTO>> readPublicWiki2(@PathVariable Long tableId, @PathVariable Long userId, @PageableDefault(size=10) Pageable pageable) {
        return ResponseEntity.ok(wikiService.readPublicWiki2(tableId, userId, pageable));
    }

	@GetMapping("/read/publics/{tableId}/{userId}")
    public ResponseEntity<List<Wiki>> readPublicWiki(@PathVariable Long tableId, @PathVariable Long userId) {
        return ResponseEntity.ok(wikiService.readPublicWiki(tableId, userId));
    }
	
	@PutMapping("/update/{wikiId}/{userId}")
    public ResponseEntity updateWiki(@PathVariable Long wikiId, @Valid @RequestBody WikiDTO wikiDto, @PathVariable Long userId) {
		wikiService.updateWiki(wikiId, wikiDto, userId);
        return ResponseEntity.ok(null);
    }
	
	@DeleteMapping("/delete/{wikiId}/{userId}")
    public ResponseEntity deleteWiki(@PathVariable Long wikiId, @PathVariable Long userId) {
		wikiService.deleteWiki(wikiId, userId);
        return ResponseEntity.ok(null);
    }
	
	@GetMapping("/search/{searchKeyword}/{userId}")
    public ResponseEntity<List<Wiki>> searchWiki(@PathVariable String searchKeyword, @PathVariable Long userId) {        
        return ResponseEntity.ok(wikiService.searchWiki(searchKeyword, userId));
    }
	
	@GetMapping("/search/all/{userId}")
    public ResponseEntity<List<String>> searchAllWiki(@PathVariable Long userId) {        
        return ResponseEntity.ok(wikiService.searchAllWiki(userId));
    }
	
	/////////////////////////////////////////////////
	// Table
	/////////////////////////////////////////////////
	@PostMapping("/create/{tableId}/{userId}")
	public ResponseEntity createTableWiki(@PathVariable Long tableId, @Valid @RequestBody WikiTableUserIdDTO wikiTableUserIdDto, @PathVariable Long userId) {
		wikiService.createTableWiki(tableId, wikiTableUserIdDto, userId);
		return ResponseEntity.ok(null);
	}
	
	@GetMapping("/read/table/{tableId}/{userId}")
    public ResponseEntity<List<Wiki>> readTableWiki(@PathVariable Long tableId, @PathVariable Long userId) {
        return ResponseEntity.ok(wikiService.readTableWiki(tableId, userId));
    }
	
//	@GetMapping("/read/table")
//	public ResponseEntity<List<Wiki>> readAllTableWiki(){
//		return ResponseEntity.ok(wikiService.readAllTableWiki(userService.getMyUserWithAuthorities()));
//	}
	
	@GetMapping("/read/table/all/{userId}")
	public ResponseEntity<Page<WikiResponseDTO>> readAllTableWiki(@PathVariable Long userId, @PageableDefault(size=10) Pageable pageable) throws Exception{
		return ResponseEntity.ok(wikiService.readAllTableWiki(userId, pageable));
	}
	
	/////////////////////////////////////////////////
	// Other
	/////////////////////////////////////////////////

	@GetMapping("/read/user/{otherUserId}/{tableId}")
    public ResponseEntity<List<Wiki>> readUserPublicWiki(@PathVariable Long otherUserId, @PathVariable Long tableId) {
        return ResponseEntity.ok(wikiService.readUserPublicWiki(otherUserId, tableId));
    }	

}
