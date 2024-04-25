package com.ssg.kms.tableuser;

import java.util.List;

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

import com.ssg.kms.mapping.GetTableMapping;
import com.ssg.kms.mapping.GetUserIdMapping;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins="*", exposedHeaders="Authorization")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tableuser")
public class TableUserController {
	private final TableUserService tableUserService;
	
	//초대 - invite
	@PostMapping("/create/{tableId}/{userId}")
	public ResponseEntity<Boolean> createTableUser(@PathVariable Long tableId, @RequestBody TableUserDTO tableUserDto, @PathVariable Long userId) {
		return ResponseEntity.ok(tableUserService.createTableUser(tableId, tableUserDto, userId));
	}	
	
	//개별 내역 확인
	@GetMapping("/teams/{tableId}/{userId}")
    public ResponseEntity readTableUserIds(@PathVariable Long tableId, @PathVariable Long userId) {
        return ResponseEntity.ok(tableUserService.readTableUserIds(tableId, userId));
    }
	
	//개별 내역 확인
	@GetMapping("/read/{tableUserId}/{userId}")
    public ResponseEntity<TableUser> readTableUser(@PathVariable Long tableUserId, @PathVariable Long userId) {
        return ResponseEntity.ok(tableUserService.readTableUser(tableUserId, userId));
    }
	
	//유저가 포함된 모든 테이블 출력
	@GetMapping("/read/{userId}")
    public ResponseEntity<List<GetTableMapping>> readAllTableUser(@PathVariable Long userId) {
        return ResponseEntity.ok(tableUserService.readAllTableUser(userId));
    }
	
	//유저가 포함된 승인한 모든 테이블 출력
	@GetMapping("/read/accept/{userId}")
    public ResponseEntity readAllAcceptTableUser(@PathVariable Long userId) {
        return ResponseEntity.ok(tableUserService.readAllAcceptTableUser(userId));
    }

	
	//참여한 모든 인원 확인
	@GetMapping("/readuser/{tableId}/{userId}")
    public ResponseEntity<List<GetUserIdMapping>> readTableUsersByTable(@PathVariable Long tableId, @PathVariable Long userId) {
        return ResponseEntity.ok(tableUserService.readTableUsersByTable(tableId, userId));
    }
	
	//초대 내역 확인
	@GetMapping("/invite/{userId}")
    public ResponseEntity<List<TableUser>> readAllInvite(@PathVariable Long userId) {
        return ResponseEntity.ok(tableUserService.readAllInvite(userId));
    }
	
	//승인 - accept 
	@PutMapping("/update/{tableUserId}/{userId}")
    public ResponseEntity<TableUser> updateTableUser(@PathVariable Long tableUserId, @PathVariable Long userId) {
        return ResponseEntity.ok(tableUserService.updateTableUser(tableUserId, userId));
    }
	
	//거절 - reject
	@DeleteMapping("/delete/{tableUserId}/{userId}")
    public ResponseEntity<Boolean> deleteTableUser(@PathVariable Long tableUserId, @PathVariable Long userId) {
        return ResponseEntity.ok(tableUserService.deleteTableUser(tableUserId, userId));
    }
	
	//추가 - admin
	@PutMapping("/admin/add/{tableId}/{otherUserId}/{userId}")
    public ResponseEntity<Boolean> addAdmin(@PathVariable Long tableId, @PathVariable Long otherUserId, @PathVariable Long userId) {
        return ResponseEntity.ok(tableUserService.addAdmin(tableId, otherUserId, userId));
    }

	//제거 - admin
	@PutMapping("/admin/remove/{tableId}/{otherUserId}/{userId}")
    public ResponseEntity<Boolean> removeAdmin(@PathVariable Long tableId, @PathVariable Long otherUserId, @PathVariable Long userId) {
        return ResponseEntity.ok(tableUserService.removeAdmin(tableId, otherUserId, userId));
    }
	
	@GetMapping("/hasCreateAuthority/{tableId}/{userId}")
	public ResponseEntity hasCreateAuthority(@PathVariable Long tableId, @PathVariable Long userId) {
		return ResponseEntity.ok(tableUserService.hasCreateAuthority(tableId, userId));
	}
	
	@GetMapping("/getMyTableIds/{userId}")
	public ResponseEntity getMyTableIds(@PathVariable Long userId) {
		return ResponseEntity.ok(tableUserService.getMyTableIds(userId));
	}

}
