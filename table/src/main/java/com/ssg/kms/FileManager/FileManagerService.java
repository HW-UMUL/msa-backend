package com.ssg.kms.FileManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileManagerService {
	
	private final FileManagerRepository fileManagerRepository;
	
	@Value("${filemanager.dir}")
	private String fileDir;
	
	@Transactional
	public List<FileManager> createFile(MultipartFile[] files, Long userId) throws Exception {
		
				
		File folder = new File(fileDir);
		
		if (!folder.exists()) {
			folder.mkdirs();
		}
		List<FileManager> fileManagers = new ArrayList<>();
		for(MultipartFile mfile : files) {
			
			//    private String originFileName;
		    String originFileName = mfile.getOriginalFilename();
		    if(!originFileName.isEmpty()) {
		    	String storeFileName = UUID.randomUUID().toString() 
		    			+ originFileName.substring(originFileName.lastIndexOf('.'));
		    	
				FileManager fileManager = FileManager.builder()
						.originFileName(fileDir)
						.storeFileName(storeFileName)
						.build();
				
				mfile.transferTo(new File(fileDir, storeFileName));
			    fileManagers.add(fileManager);		    	
		    }
//		    private String storeFileName;
		}
		 
		
		fileManagerRepository.saveAll(fileManagers);
		return fileManagers;
	}
	
	@Transactional(readOnly = true)
	public InputStreamResource readFile() throws FileNotFoundException{
		String filepath = fileDir + "/2024-02-01 15 11 39.png";	        
		        // 파일 생성 및 복사
		File file = new File(filepath);
				
		InputStreamResource isr = new InputStreamResource(new FileInputStream(file));
				
		return isr;
	}
//@Transactional(readOnly = true)
    
}
