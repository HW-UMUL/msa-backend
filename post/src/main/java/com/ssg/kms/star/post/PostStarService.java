package com.ssg.kms.star.post;

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
public class PostStarService {
    private final PostStarRepository postStarRepository;
    private final PostRepository postRepository;
    
    @Transactional
    public PostStar checkPost(Long postId, Long userId) {
    	
    	Post post = postRepository.findById(postId).get();
    	
    	PostStar postStar = postStarRepository.findByPostPostIdAndUserId(postId, userId).orElse(null);
    	
    	if(postStar == null) {
    		PostStar newPostStar = PostStar.builder()
        			.post(post)
        			.userId(userId)
        			.build();
    		
    		postStarRepository.save(newPostStar);
    		postStar = newPostStar;
    	} else {
    		postStarRepository.deleteById(postStar.getId());
    	}
    	    	
    	
    	
		return postStar;
    }
    
    @Transactional(readOnly = true)
    public int readStar(Long postId) {
    	return postStarRepository.findAllByPostPostId(postId).size();
    }

    @Transactional(readOnly = true)
    public List<GetPostMapping> readMyStar(Long userId) {
    	return postStarRepository.findPostAllByUserId(userId);
    }

    @Transactional(readOnly = true)
    public Boolean isCheck(Long postId, Long userId) {
    	Post post = postRepository.findById(postId).get();
    	if(postStarRepository.findByPostPostIdAndUserId(postId, userId) != null){
    		return true;
    	} else {
    		return false;
    	}
    }
    
    // 利먭꺼李얘린 媛쒖닔
 	public List<PostStarDTO> findTop5PostStarCounts() {
         PageRequest pageable = PageRequest.of(0, 5); // 理쒖긽�쐞 5媛쒕�� 媛��졇�삤湲� �쐞�븳 �럹�씠吏� �슂泥�
         List<Object[]> results = postStarRepository.findTop5PostStarCounts(pageable);
         return results.stream()
                 .map(this::convertToDTO)
                 .collect(Collectors.toList());
     }

     private PostStarDTO convertToDTO(Object[] result) {
         Long PostId = (Long) result[0];
         String title = (String) result[1];
         Long count = (Long) result[2];
         return new PostStarDTO(PostId, title, count);
     }
}
