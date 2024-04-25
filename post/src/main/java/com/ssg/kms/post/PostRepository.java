package com.ssg.kms.post;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {

	List<Post> findAll();

	List<Post> findAllByPostTitleContaining(String searchKeyword);

    @Query("SELECT postTitle FROM Post")
    List<String> findAllPostTitles();
    
	void deleteAllByTableId(Long tableId);

	List<Post> findAllByUserId(Long id);

	
//	List<Post> findAllByTableId(List<Long> tableIds);

	List<Post> findAllByTableId(Long tableId);

	List<Post> findAllByTableIdIn(List<Long> tableIds);
	
	
	// 알람
	@Query(value = "SELECT p.post_id FROM post p WHERE p.table_id = :tableId", nativeQuery = true)
	List<Long> findPostIdAllByTableId(Long tableId);

	@Query(value = "SELECT p.post_id FROM post p WHERE p.user_id = :userId", nativeQuery = true)	
	List<Long> findIdAllByUserId(Long userId);

//	List<Post> findAllByTableIsPublicTrueAndUserId(Long userId);

	List<Post> findAllByTableIdAndUserId(Long tableId, Long userId);
	
	
	// Page
	Page<Post> findAllByTableId(Long tableId, Pageable pageable);

	// 추천
	@Query("SELECT DISTINCT pl.post FROM PostLike pl ORDER BY pl.post DESC")
	List<Post> findByDistinctPostOrderByPostDesc();
	
	// 즐찾
	@Query("SELECT DISTINCT pl.post FROM PostStar pl ORDER BY pl.post DESC")
	List<Post> findByDistinctPostOrderByPostDesc2();
	
	// 최신
	List<Post> findTop5ByOrderByPostCreateDtDesc();
	
	
	// 理쒖떊�닚
	@Query("SELECT new com.ssg.kms.post.PostRecentDTO(p.postTitle, p.postCreateDt, p.postId) FROM Post p "
			+ "GROUP BY p.postId " + "HAVING p.postCreateDt = MIN(p.postCreateDt) "
			+ "ORDER BY p.postCreateDt DESC LIMIT 5")
	List<PostRecentDTO> findLatestPost();
	
	// 臾댄븳 �뒪�겕濡� (10媛쒖뵫 遺덈윭�샂)
	Page<Post> findByTableId(Long tableId, Pageable pageable);


}
