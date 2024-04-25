package com.ssg.kms.follow;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Table(name = "FOLLOW")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long followId;

    private Long followerId;

    private Long followeeId;

}