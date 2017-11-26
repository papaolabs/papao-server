package com.papaolabs.api.interfaces.v1.controller.response;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class PushHistoryDTO {
    private String userId;
    private Long totalElements;
    private Integer totalPages;
    private List<PushLog> pushLogs;

    public static class PushLog {
        private Long id;
        private Long postId;
        private String message;
        private String createdDate;
        private String updatedDate;
        private PushType type;

        public enum PushType {
            SEARCH, ALARM, BOOKMARK;

            public static PushType getType(String name) {
                if (StringUtils.isEmpty(name)) {
                    return null;
                }
                for (PushType type : PushType.values()) {
                    if (type.name()
                            .equals(name)) {
                        return type;
                    }
                }
                return ALARM;
            }
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getPostId() {
            return postId;
        }

        public void setPostId(Long postId) {
            this.postId = postId;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public String getUpdatedDate() {
            return updatedDate;
        }

        public void setUpdatedDate(String updatedDate) {
            this.updatedDate = updatedDate;
        }

        public PushType getType() {
            return type;
        }

        public void setType(PushType type) {
            this.type = type;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            PushLog pushLog = (PushLog) o;
            if (id != null ? !id.equals(pushLog.id) : pushLog.id != null) {
                return false;
            }
            if (postId != null ? !postId.equals(pushLog.postId) : pushLog.postId != null) {
                return false;
            }
            if (message != null ? !message.equals(pushLog.message) : pushLog.message != null) {
                return false;
            }
            if (createdDate != null ? !createdDate.equals(pushLog.createdDate) : pushLog.createdDate != null) {
                return false;
            }
            if (updatedDate != null ? !updatedDate.equals(pushLog.updatedDate) : pushLog.updatedDate != null) {
                return false;
            }
            return type == pushLog.type;
        }

        @Override
        public int hashCode() {
            int result = id != null ? id.hashCode() : 0;
            result = 31 * result + (postId != null ? postId.hashCode() : 0);
            result = 31 * result + (message != null ? message.hashCode() : 0);
            result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
            result = 31 * result + (updatedDate != null ? updatedDate.hashCode() : 0);
            result = 31 * result + (type != null ? type.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "PushLog{" +
                "id=" + id +
                ", postId=" + postId +
                ", message='" + message + '\'' +
                ", createdDate='" + createdDate + '\'' +
                ", updatedDate='" + updatedDate + '\'' +
                ", type=" + type +
                '}';
        }
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<PushLog> getPushLogs() {
        return pushLogs;
    }

    public void setPushLogs(List<PushLog> pushLogs) {
        this.pushLogs = pushLogs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PushHistoryDTO that = (PushHistoryDTO) o;
        return new EqualsBuilder()
            .append(userId, that.userId)
            .append(totalElements, that.totalElements)
            .append(totalPages, that.totalPages)
            .append(pushLogs, that.pushLogs)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(userId)
            .append(totalElements)
            .append(totalPages)
            .append(pushLogs)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("userId", userId)
            .append("totalElements", totalElements)
            .append("totalPages", totalPages)
            .append("pushLogs", pushLogs)
            .toString();
    }
}
