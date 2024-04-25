package com.ssg.kms.table;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ssg.kms.FileManager.FileManager;
import com.ssg.kms.FileManager.FileManagerService;
import com.ssg.kms.tableuser.TableUser;
import com.ssg.kms.tableuser.TableUserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TableService {
    private final TableRepository tableRepository;
    private final TableUserRepository tableUserRepository;
    
    private final FileManagerService fileManagerService;
    
    @Transactional
    public Tables createTable(TableDTO tableDto, Long userId) {
 
    	Tables table = Tables.builder()
    			.name(tableDto.getName())
    			.description(tableDto.getDescription())
    			.date(new Date())
    			.isPublic(true)
    			.build();
    	
    	TableUser tableUser = TableUser.builder()
    			.accept(true)
    			.userId(userId)
    			.table(table)
    			.isAdmin(true)
    			.build();
    	
    	tableRepository.save(table);    	
    	tableUserRepository.save(tableUser);

    	return table;
    }
    
    @Transactional(readOnly = true)
    public Tables readTable(Long tableId, Long userId) {
    	return tableRepository.findById(tableId).get();
    }

    @Transactional
    public Boolean updateTable(Long tableId, TableDTO tableDto, Long userId) {
    	
    	if(!tableUserRepository.findByUserIdAndTableId(userId, tableId).getIsAdmin()) {
    		return false;
    	}
    	
    	Tables table = tableRepository.findById(tableId).get();

    	table.setName(tableDto.getName());
    	table.setDescription(tableDto.getDescription());
    	table.setIsPublic(tableDto.getIsPublic());
    	tableRepository.save(table);
    	
		return true;
    }
    
    @Transactional
    public Boolean updateTableImage(Long tableId, MultipartFile file, Long userId) {
    	if(!tableUserRepository.findByUserIdAndTableId(userId, tableId).getIsAdmin()) {
    		return false;
    	}
    	
    	Tables table = tableRepository.findById(tableId).get();
    	
    	MultipartFile[] multipartFiles = {file};
    	FileManager fileManager;
    	try {
			fileManager = fileManagerService.createFile(multipartFiles, userId).get(0);
			table.setProfile(fileManager);
			tableRepository.save(table);
		} catch (Exception e) {
			return false;
		}
    	return true;
    }

    @Transactional
    public void deleteTable(Long tableId, Long userId) {
    	tableRepository.deleteById(tableId);    	    	
    }

	public Tables getTableInfo(Long tableId) {
		return tableRepository.findById(tableId).get();
	}
}
