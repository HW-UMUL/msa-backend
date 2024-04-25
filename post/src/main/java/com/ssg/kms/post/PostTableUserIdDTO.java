package com.ssg.kms.post;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostTableUserIdDTO {
	
    private String postTitle;

    private String postContent;

    private String tag;
    
    private String username;
    
    private List<Long> userIds;

}
