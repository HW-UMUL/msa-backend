package com.ssg.kms.like.reply;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssg.kms.reply.Reply;

public interface ReplyLikeRepository extends JpaRepository<ReplyLike, Long> {

	Optional<ReplyLike> findByReplyReplyIdAndUserId(Reply reply, Long userId);

	Set<ReplyLike> findAllByReplyReplyId(Long replyId);

	ReplyLike findByReplyReplyIdAndUserId(Long replyId, Long userId);

	void deleteAllByReplyReplyId(Long replyId);

	void deleteAllByReplyPostPostId(Long postId);

//	List<GetPostMapping> findPostAllByUserId(Long id);
}
