package com.papaolabs.api.interfaces.v1.dto;

public enum PostType {
    SYSTEM(1000), USER(2000);
    private Integer code;

    PostType(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
