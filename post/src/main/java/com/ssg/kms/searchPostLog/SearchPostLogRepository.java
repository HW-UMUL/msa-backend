package com.ssg.kms.searchPostLog;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SearchPostLogRepository extends JpaRepository<SearchPostLog, Long>{


	@Query(value = "SELECT post_Search_Log_Id, post_Search_Log_Content, user_Id, MAX(date) AS date FROM post_search_log "
			+ "WHERE user_Id = :userId GROUP BY post_Search_Log_Content ORDER BY MAX(date) DESC LIMIT 10", nativeQuery = true)
	Set<SearchPostLog> findTop10ByUserIdOrderByDateDesc(Long userId);

	@Modifying
	@Query(value = "DELETE FROM post_search_log WHERE post_Search_Log_Content = :postSearchLogContent "
	        + "AND user_Id = :userId ORDER BY date DESC LIMIT 1;", nativeQuery = true)
	void deleteTop1ByPostSearchLogContentAndUserIdOrderByDateDesc(String postSearchLogContent, Long userId);
	
}
 