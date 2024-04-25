package com.ssg.kms.alarm.reply;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssg.kms.reply.Reply;
import com.ssg.kms.websocket.NotifierWebSocketHandler;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReplyAlarmService {
	
	private final ReplyAlarmRepository replyAlarmRepository;
	private final NotifierWebSocketHandler notifierWebSocketHandler;
	
	@Transactional
	public void createAlarm(Reply reply, Long me) {
				
		Long userId = reply.getPost().getUserId();

		ReplyAlarm replyAlarm = new ReplyAlarm();
		if(!userId.equals(me)) {
			replyAlarm = ReplyAlarm.builder()
					.userId(userId)
					.reply(reply)
					.build();
			
			try {
				sendAlarm(reply, userId);	
			} catch(Exception e) {
				
			}
			
		}

		replyAlarmRepository.save(replyAlarm);
	}
	
	// 접속 중인 사람들에게 실시간으로 알람을 보냄.
	public void sendAlarm(Reply reply, Long userId) throws IOException {
		
		Map<Long, WebSocketSession> userIdSession = notifierWebSocketHandler.getUserIdSession();
		// table1. 게시글 제목 이정도?
		
		ReplyAlarmDTO alarm = ReplyAlarmDTO.builder()
				.name("reply")
				.postTitle(reply.getPost().getPostTitle())
				.content(reply.getReplyContent())
				.build();
		
		String alarmJson = convertObjectToJsonString(alarm);
		TextMessage textMessage = new TextMessage(alarmJson.toString());

		userIdSession.get(userId).sendMessage(textMessage);
	}
	
	@Transactional
	public void deleteAlarm(List<Long> postIds, Long meId) {
		if(!postIds.isEmpty())
			replyAlarmRepository.deleteByPostIdInAndUserId(postIds, meId);
	}
	
	@Transactional
	public void deleteAlarmByReplyId(Long replyId) {
		replyAlarmRepository.deleteAllByReplyReplyId(replyId);
	}
	
	@Transactional
	public void deleteAlarmByPostId(Long postId) {
		replyAlarmRepository.deleteAllByPostId(postId);
	}

	
	public String convertObjectToJsonString(ReplyAlarmDTO alarm) {

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
