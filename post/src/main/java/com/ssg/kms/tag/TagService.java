package com.ssg.kms.tag;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssg.kms.mapping.GetPostMapping;
import com.ssg.kms.post.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;
    private final TagPostRepository tagPostRepository;
    private final PostRepository postRepository;
    
    
    @Transactional(readOnly = true)
    public List<GetPostMapping> readPostByTag(String tagName) {
    	
    	Tag tag = tagRepository.findByName(tagName).get();    	
    	List<GetPostMapping> Posts = tagPostRepository.findAllByTagId(tag.getId());
    	return Posts;
    }
    
}
