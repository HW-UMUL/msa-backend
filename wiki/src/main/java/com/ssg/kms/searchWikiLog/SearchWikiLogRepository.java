package com.ssg.kms.searchWikiLog;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SearchWikiLogRepository extends JpaRepository<SearchWikiLog, Long>{


	@Query(value = "SELECT wiki_Search_Log_Id, wiki_Search_Log_Content, user_Id, MAX(date) AS date FROM wiki_search_log "
			+ "WHERE user_Id = :userId GROUP BY Wiki_Search_Log_Content ORDER BY MAX(date) DESC LIMIT 10", nativeQuery = true)
	Set<SearchWikiLog> findTop10ByUserIdOrderByDateDesc(Long userId);

	@Modifying
	@Query(value = "DELETE FROM wiki_SEARCH_LOG WHERE wiki_search_log_Content = :wikiSearchLogContent "
	        + "AND user_Id = :userId ORDER BY date DESC LIMIT 1;", nativeQuery = true)
	void deleteTop1BySearchWikiLogContentAndUserIdOrderByDateDesc(String wikiSearchLogContent, Long userId);
	
}
 