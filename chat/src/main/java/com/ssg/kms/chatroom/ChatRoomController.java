package com.ssg.kms.chatroom;

import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins="*", exposedHeaders="Authorization")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chatroom")
public class ChatRoomController {
	
	private final ChatRoomService chatRoomService;

	@PostMapping("/create/{userId}")
	public ResponseEntity<ChatRoom> createChatRoom(@RequestBody ChatRoomDTO chatRoomDto, @PathVariable Long userId) {
		return ResponseEntity.ok(chatRoomService.createChatRoom(chatRoomDto, userId));
	}
	
	@GetMapping("/read/{chatRoomId}/{userId}")
    public ResponseEntity<ChatRoom> readChatRoom(@PathVariable Long chatRoomId, @PathVariable Long userId) {
        return ResponseEntity.ok(chatRoomService.readChatRoom(chatRoomId, userId));
    }
	
	@PutMapping("/update/{chatRoomId}/{userId}")
    public ResponseEntity<ChatRoom> updateChatRoom(@PathVariable Long chatRoomId, @RequestBody ChatRoomDTO chatRoomDto, @PathVariable Long userId) {
        return ResponseEntity.ok(chatRoomService.updateChatRoom(chatRoomId, chatRoomDto, userId));
    }
	
	@DeleteMapping("/delete/{chatRoomId}/{userId}")
    public BodyBuilder deleteChatRoom(@PathVariable Long chatRoomId, @PathVariable Long userId) {
		chatRoomService.deleteChatRoom(chatRoomId, userId);
        return ResponseEntity.ok();
    }
}
