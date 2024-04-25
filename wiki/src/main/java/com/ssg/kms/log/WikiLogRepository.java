package com.ssg.kms.log;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WikiLogRepository extends JpaRepository<WikiLog, Long> {

	Set<WikiLog> findAllByWikiWikiId(Long wikiId);

	void deleteAllByWikiWikiId(Long wikiId);
	
	// 理쒖떊�닚
	@Query("SELECT new com.ssg.kms.log.WikiLogDTO(w.title, w.date, w.wiki.id) FROM WikiLog w " +
            "GROUP BY w.wiki.id " +
            "HAVING w.date = MIN(w.date) " +
            "ORDER BY w.date DESC LIMIT 5")
    List<WikiLogDTO> findLatestLogsPerWiki();

}
