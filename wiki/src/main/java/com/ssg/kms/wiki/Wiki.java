package com.ssg.kms.wiki;

import java.util.Date;

import com.ssg.kms.category.Category;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Table(name = "WIKI")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Wiki {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long wikiId;

    @NotBlank	// jakarta.validation.constraints.NotBlank
    private String wikiTitle;

    @NotBlank
    @Column(columnDefinition = "MEDIUMTEXT")
    private String wikiContent;

    @Temporal(TemporalType.TIMESTAMP)
    private Date wikiCreateDt;
    
	private Long userId;

	private Long tableId;
	
	@ManyToOne
	private Category category;

}