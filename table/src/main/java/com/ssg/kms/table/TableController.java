package com.ssg.kms.table;

import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins="*", exposedHeaders="Authorization")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/table")
public class TableController {
	private final TableService tableService;
	
	@PostMapping("/create/{userId}")
	public ResponseEntity createTable(@Valid @RequestBody TableDTO tableDto, @PathVariable Long userId) {
		tableService.createTable(tableDto, userId);
		return ResponseEntity.ok(null);
	}	
	
	@GetMapping("/read/{tableId}/{userId}")
    public ResponseEntity<Tables> readTable(@PathVariable Long tableId, @PathVariable Long userId) {
        return ResponseEntity.ok(tableService.readTable(tableId, userId));
    }
	
	@PutMapping("/update/{tableId}/{userId}")
    public ResponseEntity updateTable(@PathVariable Long tableId, @Valid @RequestBody TableDTO tableDto, @PathVariable Long userId) {
		tableService.updateTable(tableId, tableDto, userId);
        return ResponseEntity.ok(null);
    }
	
	@PutMapping("/update/{tableId}/image/{userId}")
	public ResponseEntity<Boolean> updateTableImage(@PathVariable Long tableId, @RequestPart(value = "files", required = false) MultipartFile file, @PathVariable Long userId) {
		return ResponseEntity.ok(tableService.updateTableImage(tableId, file, userId));
	}

	
	@DeleteMapping("/delete/{tableId}/{userId}")
    public BodyBuilder deleteTable(@PathVariable Long tableId, @PathVariable Long userId) {
		tableService.deleteTable(tableId, userId);
        return ResponseEntity.ok();
    }
	
//	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
	@GetMapping("/tableInfo/{tableId}/{userId}")
    public ResponseEntity getTableInfo(@PathVariable Long tableId, @PathVariable Long userId) {
        return ResponseEntity.ok(tableService.getTableInfo(tableId));
    }
}
