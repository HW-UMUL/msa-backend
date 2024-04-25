package com.ssg.kms.follow;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    
    @Transactional
    public Follow checkFollow(Long otherUserId, Long userId) {
    	
//    	User foundUser = userRepository.findByEmail(email);
    	
    	Follow follow = followRepository.findByFollowerIdAndFolloweeId(otherUserId, userId).orElse(null);
    	    	
    	if(follow == null) {
    		Follow newFollow = Follow.builder()
    				.followerId(otherUserId)
    				.followeeId(userId)
        			.build();
    		
    		followRepository.save(newFollow);
    		follow = newFollow;
    		
    	} else {
    		followRepository.deleteById(follow.getFollowId());
    	}
    	    	
    	
    	
		return follow;
    }
    
    @Transactional(readOnly = true)
    public List<Follow> readFollower(Long otherUserId, Long userId) {
    	return followRepository.findAllByFollowerId(otherUserId);
    }
    
    @Transactional(readOnly = true)
    public List<Follow> readFollowee(Long otherUserId, Long userId) {
    	return followRepository.findAllByFolloweeId(otherUserId);
    }
    
    @Transactional(readOnly = true)
    public List<Follow> readMyFollower(Long userId) {
    	return followRepository.findAllByFolloweeId(userId);
    }
    
    @Transactional(readOnly = true)
    public List<Follow> readMyFollowee(Long userId) {
    	return followRepository.findAllByFollowerId(userId);
    }
}
