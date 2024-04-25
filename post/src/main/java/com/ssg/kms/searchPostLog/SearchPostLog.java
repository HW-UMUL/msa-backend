package com.ssg.kms.searchPostLog;

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
@Table(name = "POST_SEARCH_LOG")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SearchPostLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long postSearchLogId;
	
	@NotBlank
	private String postSearchLogContent;
	
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    
    private Long userId;
	
}
