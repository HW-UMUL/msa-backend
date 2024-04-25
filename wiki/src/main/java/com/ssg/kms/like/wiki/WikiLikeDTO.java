package com.ssg.kms.like.wiki;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WikiLikeDTO {
	private Long wikiId;
    private String title;
    private Long count;
}
