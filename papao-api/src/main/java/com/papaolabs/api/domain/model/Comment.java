package com.papaolabs.api.domain.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@EntityListeners(value = {AuditingEntityListener.class })
@Table(name = "COMMENT_INFO_TB")
public class Comment {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "POST_ID")
    private String postId;
    @Column(name = "TYPE")
    private String type;
    @Column(name = "USER_ID")
    private String userId;
    @Column(name = "USER_NAME")
    private String userName;
    @Column(name = "TEXT")
    private String text;
    @Column(name = "DISPLAY_STATE")
    private Boolean isDisplay;
    @Column(name = "CREATED_DATE")
    @CreatedDate
    private Date createdDate;
    @Column(name = "UPDATED_DATE")
    @LastModifiedDate
    private Date lastModifiedDate;
}
