package com.papaolabs.push.infrastructure.persistence.jpa.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "push_log_tb")
public class PushLog extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;
    private Long userId;
    private String message;
}
