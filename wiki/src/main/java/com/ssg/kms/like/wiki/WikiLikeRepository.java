package com.ssg.kms.like.wiki;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ssg.kms.mapping.GetWikiMapping;
import com.ssg.kms.wiki.Wiki;

public interface WikiLikeRepository extends JpaRepository<WikiLike, Long> {

//	Optional<WikiLike> findByWikiAndUser(Wiki wiki, User user);

	Set<WikiLike> findAllByWikiWikiId(Long wikiId);

	void deleteAllByWikiWikiId(Long id);

	void deleteAllByUserId(Long id);

	List<GetWikiMapping> findWikiAllByUserId(Long id);

//	WikiLike findByWikiWikiIdAndUserId(Long wikiId, Long userId);

	Optional<WikiLike> findByWikiWikiIdAndUserId(Long wikiId, Long userId);

	// 醫뗭븘�슂 �닚�쐞
	@Query("SELECT w.wiki.wikiId, w.wiki.wikiTitle, COUNT(w) as count FROM WikiLike w GROUP BY w.wiki.wikiId ORDER BY count DESC")
    List<Object[]> findTop5WikiLikeCounts(Pageable pageable);
}
