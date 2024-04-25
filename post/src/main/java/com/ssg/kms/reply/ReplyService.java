package com.ssg.kms.reply;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssg.kms.alarm.reply.ReplyAlarmService;
import com.ssg.kms.like.reply.ReplyLikeRepository;
import com.ssg.kms.mapping.GetPostMappingImpl;
import com.ssg.kms.post.Post;
import com.ssg.kms.post.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReplyService {
    private final ReplyRepository replyRepository;
    private final PostRepository postRepository;
    private final ReplyLikeRepository replyLikeRepository;
    
    private final ReplyAlarmService replyAlarmService;
    
    @Transactional
    public Reply createReply(ReplyDTO replyDto, Long userId, Long postId) {
    	Optional<Post> postOptional = postRepository.findById(postId);
    	Post post = postOptional.orElseThrow(() -> new IllegalArgumentException("Post not found"));
    	Reply reply = Reply.builder()
    			.replyContent(replyDto.getContent())
    			.replyCreateDt(new Date())
    			.userId(userId)
    			.post(post)
    			.build();
    	
    	replyRepository.save(reply);

/// 알람 /////////////   	
    	replyAlarmService.createAlarm(reply, userId);
/////////////////////    	
    	
    	
		return reply;
    }
    
    @Transactional(readOnly = true)
    public Reply readReply(Long replyId, Long userId) {
		return replyRepository.findById(replyId).get();
    }
    
    @Transactional
    public Reply updateReply(Long replyId, ReplyDTO replyDto, Long userId) {
    	Reply reply = replyRepository.findById(replyId).get();
    	
    	reply.setReplyContent(replyDto.getContent());
    	reply.setReplyCreateDt(new Date());
    	
		return replyRepository.save(reply);
    }

    @Transactional
    public Reply deleteReply(Long replyId, Long userId) {
    	Reply reply = replyRepository.findById(replyId).get();
    	reply.setPost(null);
    	replyLikeRepository.deleteAllByReplyReplyId(replyId);
    	replyAlarmService.deleteAlarmByReplyId(replyId);
    	replyRepository.deleteById(replyId);
    	return reply;
    }

    @Transactional(readOnly = true)
    public List<Reply> readAllReply(Long postId, Long userId) {
		return replyRepository.findAllByPostPostId(postId);
    }
    
    @Transactional(readOnly = true)
    public List<Post> readMyReply(Long userId) {
    	List<Post> posts = new ArrayList<>();
    	Set<GetPostMappingImpl> getPostMappings = replyRepository.findPostAllByUserId(userId);
    	for(GetPostMappingImpl getPostMapping : getPostMappings) {
    		posts.add(getPostMapping.getPost());
    	}
		return posts;
    }

}
