package com.ssg.kms.star.wiki;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ssg.kms.mapping.GetWikiMapping;

public interface WikiStarRepository extends JpaRepository<WikiStar, Long> {

	Optional<WikiStar> findByWikiWikiIdAndUserId(Long wikiId, Long userId);

	Set<WikiStar> findAllByWikiWikiId(Long wikiId);

	void deleteAllByWikiWikiId(Long id);

	void deleteAllByUserId(Long id);

	List<GetWikiMapping> findWikiAllByUserId(Long id);

//	WikiStar findByWikiIdAndUserId(Long wikiId, Long userId);
	
	
	// 利먭꺼李얘린 媛쒖닔
	@Query("SELECT w.wiki.wikiId, w.wiki.wikiTitle, COUNT(w) as count FROM WikiStar w GROUP BY w.wiki.wikiId ORDER BY count DESC")
    List<Object[]> findTop5WikiStarCounts(Pageable pageable);

}
