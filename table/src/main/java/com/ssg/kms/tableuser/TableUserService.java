package com.ssg.kms.tableuser;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssg.kms.mapping.GetTableMapping;
import com.ssg.kms.mapping.GetUserIdMapping;
import com.ssg.kms.table.TableRepository;
import com.ssg.kms.table.Tables;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TableUserService {
    private final TableUserRepository tableUserRepository;
    private final TableRepository tableRepository;    
    
    @Transactional
    public Boolean createTableUser(Long tableId, TableUserDTO tableUserDto, Long userId) {
 
   
    	Tables table = tableRepository.findById(tableId).get();
    	    	
    	List<TableUser> tableUsers = new ArrayList<>();

    	if(!tableUserRepository.findByUserIdAndTableId(userId, tableId).getIsAdmin()) {
    		return false;
    	}
    	
    	for(Long user : tableUserDto.getUserId()) {

    		if(tableUserRepository.findByTableIdAndUserId(tableId, user).isEmpty()) {
    			TableUser tableUser = TableUser.builder()
    	    			.userId(user)
    	    			.table(table)
    	    			.accept(false)
    	    			.isAdmin(false)
    	    			.build();
	    		
    			tableUsers.add(tableUser);
    		}
    	}
    	tableUserRepository.saveAll(tableUsers);

    	return true;
    }
    
    @Transactional(readOnly = true)
    public List<Long> readTableUserIds(Long tableId, Long userId) {
    	List<TableUser> tableUsers = tableUserRepository.findAllByTableIdAndAcceptTrue(tableId);
    	List<Long> userIds = new ArrayList<>();
    	for(TableUser tableUser : tableUsers) {
    		userIds.add(tableUser.getUserId());

    	}
    	return userIds;
    }
    
    @Transactional(readOnly = true)
    public TableUser readTableUser(Long tableUserId, Long userId) {
    	return tableUserRepository.findById(tableUserId).get();
    }
    
    @Transactional(readOnly = true)
    public List<GetTableMapping> readAllTableUser(Long userId) {
    	
    	return tableUserRepository.findAllByUserId(userId);
    	
    }
    
    @Transactional(readOnly = true)
    public List<TableUser> readAllAcceptTableUser(Long userId) {    	
    	return tableUserRepository.findAllByUserIdAndAcceptTrue(userId);    	
    }
    
    @Transactional(readOnly = true)
    public List<TableUser> readAllInvite(Long userId) {    	
    	return tableUserRepository.findByUserIdAndAcceptFalse(userId);
    	
    }

    
    // 모든 테이블 유저
    // readTableUsersByTable
    @Transactional(readOnly = true)
    public List<GetUserIdMapping> readTableUsersByTable(Long tableId, Long userId) {
    	
    	return tableUserRepository.findAllUsersByTableIdAndAcceptTrue(tableId);
    	
    }
    
    // 승인
    @Transactional
    public TableUser updateTableUser(Long tableUserId, Long userId) {
    	
    	TableUser tableUser = tableUserRepository.findById(tableUserId).get();
    	
    	tableUser.setAccept(true);
    	
		return tableUserRepository.save(tableUser);
    }

    // 거절
    @Transactional
    public Boolean deleteTableUser(Long tableUserId, Long userId) {
    	tableUserRepository.deleteById(tableUserId);    	    	
    	return true;
    }
    
    // 권한 추가
    @Transactional
    public Boolean addAdmin(Long tableId, Long otherUserId, Long userId) {
    	TableUser tableUser = tableUserRepository.findTop1ByTableIdOrderByIdAsc(tableId);
    	if(tableUser.getUserId() == userId) {
    		TableUser admin = tableUserRepository.findByUserId(otherUserId);
    		admin.setIsAdmin(true);
    		tableUserRepository.save(admin);
    		return true;
    	} else {
    		return false;
    	}    	
    }
    
    // 권한 제거
    @Transactional
    public Boolean removeAdmin(Long tableId, Long otherUserId, Long userId) {
    	TableUser tableUser = tableUserRepository.findTop1ByTableIdOrderByIdAsc(tableId);
    	if(tableUser.getUserId() == userId) {
    		TableUser admin = tableUserRepository.findByUserId(otherUserId);
    		admin.setIsAdmin(false);
    		tableUserRepository.save(admin);
    		return true;
    	} else {
    		return false;
    	}    	
    }
    
    @Transactional
    public Boolean hasCreateAuthority(Long tableId, Long userId) {
    	TableUser tableUser = tableUserRepository.findOneByTableIdAndUserId(tableId, userId);
    	if(tableUser != null) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    @Transactional
    public List<Long> getMyTableIds(Long userId){
    	return tableUserRepository.findTableIdAllByUserId(userId);
    }
    
}
