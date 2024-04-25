package com.ssg.kms.like.post;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssg.kms.mapping.GetPostMapping;
import com.ssg.kms.post.Post;
import com.ssg.kms.post.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostLikeService {
    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    
    @Transactional
    public PostLike checkPost(Long postId, Long userId) {
    	
    	Post post = postRepository.findById(postId).get();
    	
    	PostLike postLike = postLikeRepository.findByPostPostIdAndUserId(postId, userId).orElse(null);
    	
    	if(postLike == null) {
    		PostLike newPostLike = PostLike.builder()
        			.post(post)
        			.userId(userId)
        			.build();
    		
    		postLikeRepository.save(newPostLike);
    		postLike = newPostLike;
    	} else {
    		postLikeRepository.deleteById(postLike.getId());
    	}
    	    	
    	
    	
		return postLike;
    }
    
    @Transactional(readOnly = true)
    public int readLike(Long postId) {
    	return postLikeRepository.findAllByPostPostId(postId).size();
    }
    
    @Transactional(readOnly = true)
    public List<GetPostMapping> readMyLike(Long userId) {
    	return postLikeRepository.findPostAllByUserId(userId);
    }
    
    @Transactional(readOnly = true)
    public Boolean isCheck(Long postId, Long userId) {
    	Post post = postRepository.findById(postId).get();
    	if(postLikeRepository.findByPostPostIdAndUserId(post.getPostId(), userId) != null){
    		return true;
    	} else {
    		return false;
    	}
    }
    
    ////////////////////////////////
    
	@Transactional(readOnly = true)
    public PostLike readLikePersonal(Long postId, Long userId) {
		Post post = postRepository.findById(postId).get();
    	PostLike postLike = postLikeRepository.findByPostPostIdAndUserId(postId, userId).orElse(null);
		
		if(postLike != null) {
    		return postLike;
    	}
    	    	
		return null;
    }
	
	//////////////////
	public List<PostLikeDTO> findTop5PostLikeCounts() {
		PageRequest pageable = PageRequest.of(0, 5); 
		List<Object[]> results = postLikeRepository.findTop5PostLikeCounts(pageable);
		return results.stream().map(this::convertToDTO).collect(Collectors.toList());
	}
	
	private PostLikeDTO convertToDTO(Object[] result) {
		Long PostId = (Long) result[0];
		String title = (String) result[1];
		Long count = (Long) result[2];
		return new PostLikeDTO(PostId, title, count);
	}
}
