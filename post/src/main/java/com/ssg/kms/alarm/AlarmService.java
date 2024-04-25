package com.ssg.kms.alarm;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssg.kms.alarm.post.PostAlarm;
import com.ssg.kms.alarm.post.PostAlarmRepository;
import com.ssg.kms.alarm.post.TablePostAlarmDTO;
import com.ssg.kms.alarm.reply.ReplyAlarm;
import com.ssg.kms.alarm.reply.ReplyAlarmDTO;
import com.ssg.kms.alarm.reply.ReplyAlarmRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlarmService {
	
	private final PostAlarmRepository postAlarmRepository;
	private final ReplyAlarmRepository replyAlarmRepository;
	    
    
    @Transactional(readOnly = true)
    public List<TablePostAlarmDTO> readPostAlarm(Long userId) {
    	List<PostAlarm> postAlarms = postAlarmRepository.findAllByUserId(userId);
    	List<TablePostAlarmDTO> postAlarmDTOs = new ArrayList<>();
  
    	for(PostAlarm postAlarm : postAlarms) {
    		TablePostAlarmDTO postAlarmDTO = TablePostAlarmDTO.builder()
    				.name("post")
    				.tableId(postAlarm.getPost().getTableId())
    				.postTitle(postAlarm.getPost().getPostTitle())
    				.build();
    		
    		postAlarmDTOs.add(postAlarmDTO);
    	}
		return postAlarmDTOs;
    }

    @Transactional(readOnly = true)
    public List<ReplyAlarmDTO> readReplyAlarm(Long userId) {
    	List<ReplyAlarm> replyAlarms = replyAlarmRepository.findAllByUserId(userId);
    	List<ReplyAlarmDTO> replyAlarmDTOs = new ArrayList<>();
    	  
    	for(ReplyAlarm replyAlarm : replyAlarms) {
    		ReplyAlarmDTO replyAlarmDTO = ReplyAlarmDTO.builder()
    				.name("reply")
    				.content(replyAlarm.getReply().getReplyContent())
    				.postTitle(replyAlarm.getReply().getPost().getPostTitle())
    				.build();
    		
    		replyAlarmDTOs.add(replyAlarmDTO);
    	}
		return replyAlarmDTOs;
    }

}
