package com.ssg.kms.alarm.reply;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ssg.kms.alarm.post.PostAlarm;

public interface ReplyAlarmRepository extends JpaRepository<ReplyAlarm, Long> {
	
	@Query(value = "DELETE ra FROM reply_alarm ra JOIN reply r ON ra.reply_reply_id = r.reply_id WHERE r.post_post_id IN :postIds AND ra.user_id = :userId", nativeQuery = true)
	void deleteByPostIdInAndUserId(List<Long> postIds, Long userId);
	
	@Query(value = "DELETE ra FROM reply_alarm ra JOIN reply r ON ra.reply_reply_id = r.reply_id WHERE r.post_post_id = :postId", nativeQuery = true)
	void deleteAllByPostId(Long postId);

	
	void deleteAllByReplyReplyId(Long replyId);

	List<ReplyAlarm> findAllByUserId(Long id);

//	void deleteByPostInAndUser(List<Post> posts, User user);

}
