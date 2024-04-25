package com.ssg.kms.star.post;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ssg.kms.mapping.GetPostMapping;

public interface PostStarRepository extends JpaRepository<PostStar, Long> {

	Optional<PostStar> findByPostPostIdAndUserId(Long postId, Long userId);

	Set<PostStar> findAllByPostPostId(Long postId);

	void deleteAllByPostPostId(Long id);

	void deleteAllByUserId(Long id);

	List<GetPostMapping> findPostAllByUserId(Long id);

//	PostStar findByPostPostIdAndUserId(Long postId, Long userId);

	// 利먭꺼李얘린 媛쒖닔
	@Query("SELECT w.post.postId, w.post.postTitle, COUNT(w) as count FROM PostStar w GROUP BY w.post.postId ORDER BY count DESC")
	List<Object[]> findTop5PostStarCounts(Pageable pageable);
}
