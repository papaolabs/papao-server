package com.papaolabs.api.interfaces.v1.controller.response;

import lombok.Builder;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Builder
public class ResponseType {
    private Integer code;
    private String name;

    public enum ResponseCode {
        SUCCESS(100), FAIL(-100), DUPLICATED(-101), NOTFOUND(-102), UNKNOWN(0);
        private final Integer code;

        ResponseCode(Integer code) {
            this.code = code;
        }

        public static ResponseCode getType(String name) {
            if (StringUtils.isEmpty(name)) {
                return null;
            }
            for (ResponseCode type : ResponseCode.values()) {
                if (type.code.equals(name)) {
                    return type;
                }
            }
            return UNKNOWN;
        }

        public Integer getCode() {
            return code;
        }
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ResponseType that = (ResponseType) o;
        return new EqualsBuilder()
            .append(code, that.code)
            .append(name, that.name)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(code)
            .append(name)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("code", code)
            .append("name", name)
            .toString();
    }
}
