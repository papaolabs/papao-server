package com.papaolabs.api.interfaces.v1.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

import java.time.LocalDateTime;

@Builder
@Getter
@Value
public class CommentDTO {
    private Long id;
    private Long postId;
    private String type;
    private String userId;
    private String userName;
    private String text;
    private String createdDate;
    private String lastModifiedDate;
}
