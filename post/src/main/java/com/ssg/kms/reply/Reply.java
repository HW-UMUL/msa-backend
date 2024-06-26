package com.ssg.kms.reply;

import java.util.Date;

import com.ssg.kms.post.Post;

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
@Table(name = "REPLY")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long replyId;

    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String replyContent;
    @Temporal(TemporalType.TIMESTAMP)
    private Date replyCreateDt;
    
	private Long userId;
	
	@ManyToOne //(cascade = CascadeType.ALL)    
	private Post post;

}