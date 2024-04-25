package com.ssg.kms.reply;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssg.kms.mapping.GetPostMappingImpl;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

	List<Reply> findAllByPostPostId(Long postId);

	List<Reply> findAllByUserId(Long id);

	Set<GetPostMappingImpl> findPostAllByUserId(Long id);
//	@Query(value = "SELECT DISTINCT p.post_id, p.post_title, p.post_content, p.post_create_dt, p.user_id, p.table_id FROM post p, reply r WHERE r.user_id = :userId", nativeQuery = true)	
//	List<Object> findPostAllByUserId(Long userId);	

	void deleteAllByPostPostId(Long postId);
	
}