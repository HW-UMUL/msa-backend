package com.ssg.kms.category;

import java.util.List;

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
@RequestMapping("/api/category")
public class CategoryController {
	
	private final CategoryService categoryService;
	
	@GetMapping("/read/{categoryName}/{userId}")
    public ResponseEntity readWikiByCategory(@PathVariable String categoryName, @PathVariable Long userId) {
		return ResponseEntity.ok(categoryService.readWikiByCategory(categoryName, userId));
    }
	@GetMapping("/readall/{userId}")
    public ResponseEntity<List<Category>> readAllCategory(@PathVariable Long userId) {
		return ResponseEntity.ok(categoryService.readAllCategory(userId));
    }

}
