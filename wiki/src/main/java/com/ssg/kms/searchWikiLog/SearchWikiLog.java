package com.ssg.kms.searchWikiLog;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "WIKI_SEARCH_LOG")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SearchWikiLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long wikiSearchLogId;
	
	@NotBlank
	private String wikiSearchLogContent;
	
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    
    private Long userId;
	
}
