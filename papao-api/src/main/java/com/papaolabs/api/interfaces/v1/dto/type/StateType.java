package com.papaolabs.api.interfaces.v1.dto.type;

public enum StateType {
    PROCESS("보호중"), RETURN("종료(반환)"), NATURALDEATH("종료(자연사)"), EUTHANASIA("종료(안락사)"), ADOPTION("종료(입양)");
    private final String code;

    StateType(String code) {
        this.code = code;
    }
}
