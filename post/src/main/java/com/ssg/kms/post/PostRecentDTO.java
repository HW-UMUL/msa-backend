package com.ssg.kms.post;

import java.util.Date;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostRecentDTO {
	private String title;
    private Date date;
    private Long postId;
}

