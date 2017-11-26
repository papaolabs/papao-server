package com.papaolabs.api.interfaces.v1.controller.request;

public class CommentRequest {
    private String userId;
    private String text;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CommentRequest that = (CommentRequest) o;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) {
            return false;
        }
        return text != null ? text.equals(that.text) : that.text == null;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CommentRequest{" +
            "userId='" + userId + '\'' +
            ", text='" + text + '\'' +
            '}';
    }
}
