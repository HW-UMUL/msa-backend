package com.ssg.kms.chat;

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
@RequestMapping("/api/chat")
public class ChatController {

	private final ChatService chatService;

	@PostMapping("/create/{chatRoomId}/{userId}")
	public ResponseEntity<Chat> createChat(@PathVariable Long chatRoomId, @RequestBody ChatDTO chatDto, @PathVariable Long userId) {
		return ResponseEntity.ok(chatService.createChat(chatRoomId, chatDto, userId));
	}
	
	@GetMapping("/read/{chatRoomId}/{userId}")
    public ResponseEntity<List<Chat>> readChat(@PathVariable Long chatRoomId, @PathVariable Long userId) {
        return ResponseEntity.ok(chatService.readChat(chatRoomId, userId));
    }
	
	@GetMapping("/read/recent/{chatRoomId}/{userId}")
    public ResponseEntity<Chat> readRecentChat(@PathVariable Long chatRoomId, @PathVariable Long userId) {
        return ResponseEntity.ok(chatService.readRecentChat(chatRoomId, userId));
    }


	@DeleteMapping("/delete/{chatId}/{userId}")
    public BodyBuilder deleteChatRoom(@PathVariable Long chatId, @PathVariable Long userId) {
		chatService.deleteChat(chatId, userId);
        return ResponseEntity.ok();
    }
}
