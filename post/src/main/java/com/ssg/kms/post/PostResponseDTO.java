package com.ssg.kms.post;

import java.util.Date;

import com.ssg.kms.tag.Tag;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDTO {
	private Long postId;
    private String postTitle;
    private String postContent;
    private Date postCreateDt;
    private Long userId;
    private Long tableId;
    private String tag;
    
    public static PostResponseDTO from(Post post, Tag tag) {
    	PostResponseDTO dto = new PostResponseDTO();
        dto.setPostId(post.getPostId());
        dto.setPostTitle(post.getPostTitle());
        dto.setPostContent(post.getPostContent());
        dto.setPostCreateDt(post.getPostCreateDt());
        dto.setUserId(post.getUserId());
        dto.setTableId(post.getTableId());
        if (tag.getId() != null) {
            dto.setTag(tag.getName());
        }
        return dto;
    }
}
