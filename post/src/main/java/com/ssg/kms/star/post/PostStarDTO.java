package com.ssg.kms.star.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostStarDTO {
    private Long postId;
    private String title;
    private Long starCount;
}
