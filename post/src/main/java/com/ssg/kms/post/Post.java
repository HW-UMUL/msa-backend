package com.ssg.kms.post;

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
@Table(name = "POST")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long postId;

    @NotBlank	// jakarta.validation.constraints.NotBlank
    private String postTitle;

    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String postContent;

    @Temporal(TemporalType.TIMESTAMP)
    private Date postCreateDt;
    
    private Long userId;
    
    private String username;
    
    private Long tableId;
    
    private Boolean isPublic;

}