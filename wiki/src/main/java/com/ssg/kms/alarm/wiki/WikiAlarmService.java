package com.ssg.kms.alarm.wiki;

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
import com.ssg.kms.websocket.NotifierWebSocketHandler;
import com.ssg.kms.wiki.Wiki;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WikiAlarmService {
	
	private final WikiAlarmRepository wikiAlarmRepository;
	private final NotifierWebSocketHandler notifierWebSocketHandler;
	
	@Transactional
	public void createAlarm(Wiki wiki, List<Long> users, Long me) {
		
		List<WikiAlarm> wikiAlarms = new ArrayList<>();
		
		for(Long user : users) {
			if(!user.equals(me)) {
				WikiAlarm wikiAlarm = WikiAlarm.builder()
						.userId(user)
						.wiki(wiki)
						.build();
				wikiAlarms.add(wikiAlarm);
				
				try {
					sendAlarm(wiki, user);	
				} catch(Exception e) {

				}
				
			}
		}
		
		wikiAlarmRepository.saveAll(wikiAlarms);
	}
	
	// 접속 중인 사람들에게 실시간으로 알람을 보냄.
	public void sendAlarm(Wiki wiki, Long userId) throws IOException {
		
		Map<Long, WebSocketSession> userIdSession = notifierWebSocketHandler.getUserIdSession();
		// table1. 게시글 제목 이정도?
		TableWikiAlarmDTO alarm = TableWikiAlarmDTO.builder()
				.name("wiki")
				.tableId(wiki.getTableId())
				.wikiTitle(wiki.getWikiTitle())
				.build();
		
		String alarmJson = convertObjectToJsonString(alarm);
		TextMessage textMessage = new TextMessage(alarmJson.toString());

		userIdSession.get(userId).sendMessage(textMessage);
	}
	
	@Transactional
	public void deleteAlarm(List<Long> wikiIds, Long meId) {
		if(!wikiIds.isEmpty())
			wikiAlarmRepository.deleteByWikiIdInAndUserId(wikiIds, meId);
	}
	
	@Transactional
	public void deleteAlarmByWikiId(Long wikiId) {
		wikiAlarmRepository.deleteAllByWikiId(wikiId);
	}
	
	public String convertObjectToJsonString(TableWikiAlarmDTO alarm) {

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
