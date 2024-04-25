package com.ssg.kms.like.reply;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssg.kms.reply.Reply;
import com.ssg.kms.reply.ReplyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReplyLikeService {
    private final ReplyLikeRepository replyLikeRepository;
    private final ReplyRepository replyRepository;
    
    @Transactional
    public ReplyLike checkReply(Long replyId, Long userId) {
    	
    	Reply reply = replyRepository.findById(replyId).get();
    	
    	ReplyLike replyLike = replyLikeRepository.findByReplyReplyIdAndUserId(reply.getReplyId(), userId);
    	
    	if(replyLike == null) {
    		ReplyLike newReplyLike = ReplyLike.builder()
        			.reply(reply)
        			.userId(userId)
        			.build();

    		replyLikeRepository.save(newReplyLike);
    		
    		replyLike = newReplyLike;
    	} else {
    		replyLikeRepository.deleteById(replyLike.getId());
    	}
    	    	
    	
    	
		return replyLike;
    }
    
    @Transactional(readOnly = true)
    public int readLike(Long replyId) {
    	return replyLikeRepository.findAllByReplyReplyId(replyId).size();
    }
    
    @Transactional(readOnly = true)
    public Boolean isCheck(Long replyId, Long userId) {
    	Reply reply = replyRepository.findById(replyId).get();

    	if(replyLikeRepository.findByReplyReplyIdAndUserId(replyId, userId) != null){
    		return true;
    	} else {
    		return false;
    	}
    }
    

//    @Transactional(readOnly = true)
//    public List<GetPostMapping> readMyLike(Optional<User> user) {
//    	return replyLikeRepository.findPostAllByUserId(user.get().getId());
//    }

}
