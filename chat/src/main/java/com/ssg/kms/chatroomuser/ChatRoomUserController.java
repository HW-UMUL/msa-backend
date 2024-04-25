package com.ssg.kms.chatroomuser;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins="*", exposedHeaders="Authorization")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chatroomuser")
public class ChatRoomUserController {
	
	private final ChatRoomUserService chatRoomUserService;

	// 초대하기
	@PostMapping("/create/{chatRoomId}/{userId}")
	public ResponseEntity<List<ChatRoomUser>> createChatRoomUser(@PathVariable Long chatRoomId, @RequestBody ChatRoomUserDTO chatRoomUserDto, @PathVariable Long userId) {
		return ResponseEntity.ok(chatRoomUserService.createChatRoomUser(chatRoomId, chatRoomUserDto, userId));
	}
	
	// 전체 가져오기
	@GetMapping("/read/{chatRoomId}/{userId}")
    public ResponseEntity<List<ChatRoomUser>> readChatRoomUser(@PathVariable Long chatRoomId, @PathVariable Long userId) {
        return ResponseEntity.ok(chatRoomUserService.readChatRoomUser(chatRoomId, userId));
    }
	
	// 유저가 가지고 있는 채팅룸 가져오기
	@GetMapping("/read/{userId}")
    public ResponseEntity<List<ChatRoomUser>> readAllChatRoom(@PathVariable Long userId) {
        return ResponseEntity.ok(chatRoomUserService.readAllChatRoom(userId));
    }
		
	// 강퇴
	@DeleteMapping("/delete/{chatRoomUserId}/{userId}")
    public BodyBuilder deleteChatRoom(@PathVariable Long chatRoomUserId, @PathVariable Long userId) {
		chatRoomUserService.deleteChatRoomUser(chatRoomUserId, userId);
        return ResponseEntity.ok();
    }
}
