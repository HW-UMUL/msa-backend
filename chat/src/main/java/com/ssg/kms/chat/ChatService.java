package com.ssg.kms.chat;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssg.kms.chatroom.ChatRoom;
import com.ssg.kms.chatroom.ChatRoomRepository;
import com.ssg.kms.chatroomuser.ChatRoomUserRepository;
import com.ssg.kms.websocket.ChatWebSocketHandler;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {
	
	private final ChatRepository chatRepository;
	private final ChatRoomRepository chatRoomRepository;
	private final ChatRoomUserRepository chatRoomUserRepository;
	
    @Transactional
    public Chat createChat(Long chatRoomId, ChatDTO chatDto, Long userId) {
    	
    	ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).get();

    	Chat chat = Chat.builder()
    			.chatContent(chatDto.getContent())
    			.chatCreateDt(new Date())
    			.userId(userId)
    			.chatRoom(chatRoom)
    			.build();
    	
    	chatRepository.save(chat);
    	
/// 알람 /////////////   	
    	List<Long> chatRoomUserIds = chatRoomUserRepository.findUserIdsByChatRoomId(chatRoomId);
    	sendChat(chat, chatRoomUserIds);
/////////////////////        	
    	    	
		return chat;
    }
    

    @Transactional(readOnly = true)
    public List<Chat> readChat(Long chatRoomId, Long userId) {
    	return chatRepository.findAllByChatRoomId(chatRoomId);
    }
    
    @Transactional(readOnly = true)
    public Chat readRecentChat(Long chatRoomId, Long userId) {
    	return chatRepository.findTopByChatRoomIdOrderByidDesc(chatRoomId);
    }


    @Transactional
    public void deleteChat(Long chatId, Long userId) {
    	chatRepository.deleteById(chatId);
    }
    
    
    
    ///// 알람 //////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    
	private final ChatWebSocketHandler chatWebSocketHandler;
	private final JSONParser jsonParser = new JSONParser();
	
	@Transactional
	public void sendChat(Chat chat, List<Long> users) {
		
		for(Long user : users) {
			try {
				sendChat(chat, user);
			} catch(Exception e) {
									
			}
		}
	}
	
	
	// 접속 중인 사람들에게 실시간으로 알람을 보냄.
	public void sendChat(Chat chat, Long userId) throws IOException {
		
		Map<Long, WebSocketSession> userIdSession = chatWebSocketHandler.getUserIdSession();
		
		String chatJsonString = convertObjectToJsonString(chat);

		JSONObject chatJson;
		try {
			chatJson = (JSONObject) jsonParser.parse(chatJsonString);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"); // ISO 8601 형식 지정
			sdf.setTimeZone(TimeZone.getTimeZone("UTC")); // UTC 타임존 설정
			String isoString = sdf.format(chat.getChatCreateDt()); // Date 객체를 ISO 8601 형식의 문자열로 변환

			chatJson.replace("date", isoString);

			TextMessage textMessage = new TextMessage(chatJson.toString());

			userIdSession.get(userId).sendMessage(textMessage);
		} catch (ParseException e) {
		}

	}
	
	public String convertObjectToJsonString(Chat chat) {

		ObjectMapper mapper = new ObjectMapper();
		String jsonStr = null;
		try {
			jsonStr = mapper.writeValueAsString(chat);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return jsonStr;
	}
}
