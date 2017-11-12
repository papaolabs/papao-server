package com.papaolabs.push.interfaces.dto;

import java.util.List;

public class PushHistory {
    private Long userId;
    private List<PushLog> pushLogs;

    public static class PushLog {
        private String message;
        private String createdDate;
        private String updatedDate;

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

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            PushLog pushLog = (PushLog) o;
            if (message != null ? !message.equals(pushLog.message) : pushLog.message != null) {
                return false;
            }
            if (createdDate != null ? !createdDate.equals(pushLog.createdDate) : pushLog.createdDate != null) {
                return false;
            }
            return updatedDate != null ? updatedDate.equals(pushLog.updatedDate) : pushLog.updatedDate == null;
        }

        @Override
        public int hashCode() {
            int result = message != null ? message.hashCode() : 0;
            result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
            result = 31 * result + (updatedDate != null ? updatedDate.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "PushLog{" +
                "message='" + message + '\'' +
                ", createdDate='" + createdDate + '\'' +
                ", updatedDate='" + updatedDate + '\'' +
                '}';
        }
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
        PushHistory that = (PushHistory) o;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) {
            return false;
        }
        return pushLogs != null ? pushLogs.equals(that.pushLogs) : that.pushLogs == null;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (pushLogs != null ? pushLogs.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PushHistory{" +
            "userId=" + userId +
            ", pushLogs=" + pushLogs +
            '}';
    }
}
