package com.ssg.kms.like.post;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ssg.kms.mapping.GetPostMapping;
import com.ssg.kms.post.Post;
import org.springframework.data.jpa.repository.Query;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

//	Optional<PostLike> findByPostAndUser(Post post, User user);

	Set<PostLike> findAllByPostPostId(Long postId);

	void deleteAllByPostPostId(Long id);

	void deleteAllByUserId(Long id);

	List<GetPostMapping> findPostAllByUserId(Long id);

	Optional<PostLike> findByPostPostIdAndUserId(Long postId, Long userId);

//	Optional<Post> findByPostAndUserId(Post post, Long userId);
//
	@Query("SELECT w.post.postId, w.post.postTitle, COUNT(w) as count FROM PostLike w GROUP BY w.post.postId ORDER BY count DESC")
	List<Object[]> findTop5PostLikeCounts(Pageable pageable);
}
