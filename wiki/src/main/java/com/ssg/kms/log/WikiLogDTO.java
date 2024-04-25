package com.ssg.kms.log;

import java.util.Date;

import com.ssg.kms.wiki.Wiki;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class WikiLogDTO {
	
	private String title;
    private Date date;
    private Long wikiId;

}
