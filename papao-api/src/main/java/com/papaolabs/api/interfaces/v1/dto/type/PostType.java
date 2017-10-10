package com.papaolabs.api.interfaces.v1.dto.type;

public enum PostType {
    SYSTEM("01"), ABSENCE("02"), PROTECT("03");
    private String code;

    PostType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
