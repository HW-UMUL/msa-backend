package com.ssg.kms.star.wiki;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WikiStarDTO {
    private Long wikiId;
    private String title;
    private Long starCount;
}
