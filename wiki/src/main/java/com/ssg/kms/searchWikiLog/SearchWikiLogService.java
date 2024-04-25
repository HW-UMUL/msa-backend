package com.ssg.kms.searchWikiLog;

import java.util.Date;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SearchWikiLogService {
	
	private final SearchWikiLogRepository searchWikiLogRepository;

//	public SearchLog sendSearchLog(SearchLogDTO searchLogDTO, Optional<User> user) {
//	SearchLog searchLog = SearchLog.builder()
//			.content(searchLogDTO.getContent())
//			.date(searchLogDTO.getDate())
//			.user(user.get())
//			.build();
//	
//	searchLogRepository.save(searchLog);
//	return searchLog;
//	}
	
	@Transactional
	public SearchWikiLog saveSearchWikiLog(SearchWikiLog searchLog, Long userId) {
			searchLog.setUserId(userId);
			searchLog.setDate(new Date());
		
			searchWikiLogRepository.save(searchLog);
	
	return searchLog;
	}
	
	@Transactional(readOnly = true)
	public Set<SearchWikiLog> readSearchWikiLog(Long userId) {
		
	    return searchWikiLogRepository.findTop10ByUserIdOrderByDateDesc(userId);
	}
 
	@Transactional // SearchLogIdAndUserId 두개의 조건을 부여해 삭제해야한다.
	public SearchWikiLog deleteSearchWikiLog(String content, Long userId) {
		searchWikiLogRepository.deleteTop1BySearchWikiLogContentAndUserIdOrderByDateDesc(content, userId);
		SearchWikiLog searchWikiLog = new SearchWikiLog();
		
		return searchWikiLog;
	}

}