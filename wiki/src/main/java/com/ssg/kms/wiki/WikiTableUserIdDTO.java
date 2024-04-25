package com.ssg.kms.wiki;

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
public class WikiTableUserIdDTO {
	
    private String title;

    private String content;
    
    private String category;
    
    private List<Long> userIds;
}
