package com.ssg.kms.tableuser;

import com.ssg.kms.table.Tables;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Table(name = "TABLE_USER")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TableUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;
    
    private Boolean accept;

    private Boolean isAdmin;
    
    private Long userId;
    
    @ManyToOne
    @JoinColumn(name = "table_id")
    private Tables table;
}