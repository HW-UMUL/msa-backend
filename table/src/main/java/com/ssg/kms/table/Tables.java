package com.ssg.kms.table;

import java.util.Date;

import com.ssg.kms.FileManager.FileManager;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Table(name = "TABLES")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Tables {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    @NotBlank	// jakarta.validation.constraints.NotBlank
    private String name;

    @NotBlank
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    
    private Boolean isPublic;
    
    @OneToOne
    private FileManager profile;
}