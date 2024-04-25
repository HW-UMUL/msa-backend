package com.ssg.kms.searchPostLog;

import java.util.Date;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SearchPostLogService {
	
	private final SearchPostLogRepository searchLogRepository;

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
	public SearchPostLog saveSearchLog(SearchPostLog searchLog, Long userId) {
			searchLog.setUserId(userId);
			searchLog.setDate(new Date());
		
	searchLogRepository.save(searchLog);
	
	return searchLog;
	}
	
	@Transactional(readOnly = true)
	public Set<SearchPostLog> readSearchLog(Long userId) {
		
	    return searchLogRepository.findTop10ByUserIdOrderByDateDesc(userId);
	
	}
 
	@Transactional // SearchLogIdAndUserId 두개의 조건을 부여해 삭제해야한다.
	public SearchPostLog deleteSearchLog(String content, Long userId) {
		searchLogRepository.deleteTop1ByPostSearchLogContentAndUserIdOrderByDateDesc(content, userId);
		SearchPostLog searchLog = new SearchPostLog();
		
		return searchLog;
	}

}