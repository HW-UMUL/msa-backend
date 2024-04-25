package com.ssg.kms.websocket;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NotifierWebSocketHandler extends TextWebSocketHandler {

	private Map<WebSocketSession, Long> sessionUserId = new HashMap<>();
	private Map<Long, WebSocketSession> userIdSession = new HashMap<>();

	private final JSONParser jsonParser = new JSONParser();

	public Map<Long, WebSocketSession> getUserIdSession(){
		return userIdSession;
	}
	
	public Map<WebSocketSession, Long> getSessionUserId(){
		return sessionUserId;
	}

	
	// 연결시
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		sessionUserId.put(session, null);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		Long userId = sessionUserId.get(session);
		sessionUserId.remove(session);
		userIdSession.remove(userId);
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

		JSONObject jsonObj = (JSONObject) jsonParser.parse(message.getPayload());
		Long username;
		Long auth;
		try {
			auth = (Long) jsonObj.get("auth");
			sessionUserId.replace(session, auth);
			userIdSession.put(auth, session);
			return;
		} catch (Exception e) {

		}
	}
}
