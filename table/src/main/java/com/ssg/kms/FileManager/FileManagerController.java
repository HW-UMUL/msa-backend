package com.ssg.kms.FileManager;

import java.io.FileNotFoundException;
import java.util.List;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins="*", exposedHeaders="Authorization")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/file")
public class FileManagerController {
	private final FileManagerService fileManagerService;

	/////////////////////////////////////////////////
	// Own
	/////////////////////////////////////////////////
	@PostMapping("/create")
	public ResponseEntity<List<FileManager>> createFile(@PathVariable Long userId, @RequestPart(value = "files", required = false) MultipartFile[] files) throws Exception {
		return ResponseEntity.ok(fileManagerService.createFile(files, userId));
	}

	@GetMapping("/read")
    public ResponseEntity<InputStreamResource> readFile() throws FileNotFoundException {
		
        return ResponseEntity.ok(fileManagerService.readFile());
    }

}
