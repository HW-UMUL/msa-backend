package com.ssg.kms.tag;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssg.kms.post.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TagPostService {
    private final TagRepository tagRepository;
    private final TagPostRepository tagPostRepository;
    private final PostRepository postRepository;
    
    
    @Transactional(readOnly = true)
    public String readTagByPostId(Long postId) {
    	List<TagPost> tagPosts = tagPostRepository.findAllByPostPostId(postId);
    	StringBuilder tagPostsSB = new StringBuilder();
    	for(TagPost tagPost : tagPosts) {
    		tagPostsSB.append(tagPost.getTag().getName());
    		tagPostsSB.append(" ");
    	}
    	return tagPostsSB.toString();
    }
    
	@Transactional(readOnly = true)
    public boolean deleteByPostId(Long postId) {
    	try {
	    	tagPostRepository.deleteById(postId);
	    	return true;
    	} catch (Exception e) {
    		e.printStackTrace();
    		return false;
    	}
    }
    
}
