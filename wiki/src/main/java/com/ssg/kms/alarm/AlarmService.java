package com.ssg.kms.alarm;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssg.kms.alarm.wiki.TableWikiAlarmDTO;
import com.ssg.kms.alarm.wiki.WikiAlarm;
import com.ssg.kms.alarm.wiki.WikiAlarmRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlarmService {
	
	private final WikiAlarmRepository wikiAlarmRepository;
	    
    


    @Transactional(readOnly = true)
    public List<TableWikiAlarmDTO> readWikiAlarm(Long userId) {
    	List<WikiAlarm> wikiAlarms = wikiAlarmRepository.findAllByUserId(userId);
    	List<TableWikiAlarmDTO> wikiAlarmDTOs = new ArrayList<>();
    	  
    	for(WikiAlarm wikiAlarm : wikiAlarms) {
    		TableWikiAlarmDTO wikiAlarmDTO = TableWikiAlarmDTO.builder()
    				.name("wiki")
    				.tableId(wikiAlarm.getWiki().getTableId())
    				.wikiTitle(wikiAlarm.getWiki().getWikiTitle())
    				.build();
    		
    		wikiAlarmDTOs.add(wikiAlarmDTO);
    	}
		return wikiAlarmDTOs;
    }


}
