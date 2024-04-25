package com.ssg.kms.log;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssg.kms.wiki.Wiki;
import com.ssg.kms.wiki.WikiRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WikiLogService {
	private final WikiRepository wikiRepository;
    private final WikiLogRepository wikiLogRepository;
    
    @Transactional
    public WikiLog createWikiLog(Wiki wiki, Long userId) {
    	WikiLog wikiLog = WikiLog.builder()
    			.title(wiki.getWikiTitle())
    			.content(wiki.getWikiContent())
    			.date(new Date())
    			.wiki(wiki)
    			.build();
    	
		return wikiLogRepository.save(wikiLog);
    }
    
    @Transactional(readOnly = true)
    public WikiLog readWikiLog(Long wikiLogId, Long userId) {
		return wikiLogRepository.findById(wikiLogId).get();
    }
    
    @Transactional(readOnly = true)
    public Set<WikiLog> readWikiLogByWikiId(Long wikiId, Long userId) {
		return wikiLogRepository.findAllByWikiWikiId(wikiId);
    }


    @Transactional
    public WikiLog deleteWikiLog(Long wikiLogId, Long userId) {    	
    	WikiLog wikiLog = wikiLogRepository.findById(wikiLogId).get();
    	
    	wikiLog.setWiki(null);
    	wikiLogRepository.deleteById(wikiLogId);
    	return wikiLog;
    }    
    
    // 理쒖떊�닚
    public List<WikiLogDTO> findLatestLogsPerWiki() {
        return wikiLogRepository.findLatestLogsPerWiki();
    }

}
