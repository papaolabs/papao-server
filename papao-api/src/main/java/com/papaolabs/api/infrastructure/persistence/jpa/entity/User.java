package com.papaolabs.api.infrastructure.persistence.jpa.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "user_tb")
public class User extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String uid;
    private String nickName;
    private String phone;
    private String email;
}
