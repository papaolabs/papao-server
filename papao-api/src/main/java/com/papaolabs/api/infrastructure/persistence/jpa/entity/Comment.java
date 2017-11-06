package com.papaolabs.api.infrastructure.persistence.jpa.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "comment_tb")
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String postId;
    private String type;
    private String userId;
    private String userName;
    private String text;
    private Boolean isDisplay;
}
