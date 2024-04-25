package com.ssg.kms.wiki;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WikiRepository extends JpaRepository<Wiki, Long> {

    @Query("SELECT wikiTitle FROM Wiki")
    List<String> findAllWikiTitles();
    
	void deleteAllByTableId(Long tableId);

	List<Wiki> findAllByCategoryName(String categoryName);

	List<Wiki> findAllByUserId(Long id);

//	List<Wiki> findAllByTableIsPublicTrue();

	List<Wiki> findAllByTableId(Long tableId);
	
	Page<Wiki> findByTableId(Long tableId, Pageable pageable);


	Page<Wiki> findAllByTableIdIn(List<Long> tableIds, Pageable pageable);

	// �븣�엺
	@Query(value = "SELECT w.wiki_id FROM wiki w WHERE w.table_id = :tableId", nativeQuery = true)
	List<Long> findWikiIdAllByTableId(Long tableId);

//	List<Wiki> findAllByWikiTitleContaining(String searchKeyword);

//	List<Wiki> findAllByTableIsPublicTrueAndUserId(Long userId);

	List<Wiki> findAllByTableIdAndUserId(Long tableId, Long userId);
	
	List<Wiki> findAllByWikiTitleContaining(String searchKeyword);

}
