package com.ssg.kms.alarm.post;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssg.kms.post.Post;
import com.ssg.kms.websocket.NotifierWebSocketHandler;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostAlarmService {
	
	private final PostAlarmRepository postAlarmRepository;
	private final NotifierWebSocketHandler notifierWebSocketHandler;
	
	@Transactional
	public void createAlarm(Post post, List<Long> users, Long me) {
		
		List<PostAlarm> postAlarms = new ArrayList<>();
		
		for(Long user : users) {
			if(!user.equals(me)) {

				PostAlarm postAlarm = PostAlarm.builder()
						.userId(user)
						.post(post)
						.build();
				postAlarms.add(postAlarm);
				
				try {
					sendAlarm(post, user);	
				} catch(Exception e) {
					
				}
				
			}
		}
		
		postAlarmRepository.saveAll(postAlarms);
	}
	
	// 접속 중인 사람들에게 실시간으로 알람을 보냄.
	public void sendAlarm(Post post, Long user) throws IOException {
		
		Map<Long, WebSocketSession> userIdSession = notifierWebSocketHandler.getUserIdSession();
		// table1. 게시글 제목 이정도?
		
		TablePostAlarmDTO alarm = TablePostAlarmDTO.builder()
				.name("post")
				.tableId(post.getTableId())
				.postTitle(post.getPostTitle())
				.build();
		
		String alarmJson = convertObjectToJsonString(alarm);
		TextMessage textMessage = new TextMessage(alarmJson.toString());

		userIdSession.get(user).sendMessage(textMessage);
	}
	
	@Transactional
	public void deleteAlarm(List<Long> postIds, Long meId) {
		if(!postIds.isEmpty())
			postAlarmRepository.deleteByPostIdInAndUserId(postIds, meId);
	}
	
	@Transactional
	public void deleteAlarmByPostId(Long postId) {
		postAlarmRepository.deleteAllByPostId(postId);
	}
	
	public String convertObjectToJsonString(TablePostAlarmDTO alarm) {

		ObjectMapper mapper = new ObjectMapper();
		String jsonStr = null;
		try {
			jsonStr = mapper.writeValueAsString(alarm);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return jsonStr;
	}
	
}
