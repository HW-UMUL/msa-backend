package com.ssg.kms.alarm;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssg.kms.alarm.post.TablePostAlarmDTO;
import com.ssg.kms.alarm.reply.ReplyAlarmDTO;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins="*", exposedHeaders="Authorization")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/alarm")
public class AlarmController {
	private final AlarmService alarmService;
	
	/////////////////////////////////////////////////
	
	@GetMapping("/read/post/{userId}")
    public ResponseEntity<List<TablePostAlarmDTO>> readPostAlarm(@PathVariable Long userId) {
        return ResponseEntity.ok(alarmService.readPostAlarm(userId));
    }
	
	@GetMapping("/read/reply/{userId}")
    public ResponseEntity<List<ReplyAlarmDTO>> readReplyAlarm(@PathVariable Long userId) {
        return ResponseEntity.ok(alarmService.readReplyAlarm(userId));
    }

}
