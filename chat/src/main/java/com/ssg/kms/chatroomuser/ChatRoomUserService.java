package com.ssg.kms.chatroomuser;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssg.kms.chatroom.ChatRoom;
import com.ssg.kms.chatroom.ChatRoomRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatRoomUserService {
	
	private final ChatRoomRepository chatRoomRepository;
	private final ChatRoomUserRepository chatRoomUserRepository;
	
    @Transactional
    public List<ChatRoomUser> createChatRoomUser(Long chatRoomId, ChatRoomUserDTO chatRoomUserDto, Long userId) {
    	
    	ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).get();
    	
    	List<ChatRoomUser> chatRoomUsers = new ArrayList<>();
    	
    	for(Long foundUser : chatRoomUserDto.getUserIds()) {
    		if(foundUser != null && chatRoomUserRepository.findByChatRoomIdAndUserId(chatRoomId, foundUser).isEmpty()) {
	    		ChatRoomUser chatRoomUser = ChatRoomUser.builder()
	        			.userId(foundUser)
	        			.chatRoom(chatRoom)
	        			.build();
	    		
	    		chatRoomUsers.add(chatRoomUser);
    		}
    	}
    	
    	chatRoomUserRepository.saveAll(chatRoomUsers);
    	    	
    	    	
		return chatRoomUsers;
    }
    

    @Transactional(readOnly = true)
    public List<ChatRoomUser> readChatRoomUser(Long chatRoomId, Long userId) {
    	return chatRoomUserRepository.findAllByChatRoomId(chatRoomId);
    }
    
    @Transactional(readOnly = true)
    public List<ChatRoomUser> readAllChatRoom(Long userId) {
    	
    	return chatRoomUserRepository.findAllByUserId(userId);
    }
    
    @Transactional
    public void deleteChatRoomUser(Long chatRoomUserId, Long userId) {
    	
    	chatRoomUserRepository.deleteById(chatRoomUserId);

    }
}
