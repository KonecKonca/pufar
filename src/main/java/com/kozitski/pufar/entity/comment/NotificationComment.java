package com.kozitski.pufar.entity.comment;

public class NotificationComment {
    private long commentId;
    private long userId;
    private String comment;

    public NotificationComment() {
    }
    public NotificationComment(long commentId, long userId, String comment) {
        this.commentId = commentId;
        this.userId = userId;
        this.comment = comment;
    }

    public long getCommentId() {
        return commentId;
    }
    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }
    public long getUserId() {
        return userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NotificationComment)) return false;

        NotificationComment that = (NotificationComment) o;

        if (commentId != that.commentId) return false;
        if (userId != that.userId) return false;
        return comment != null ? comment.equals(that.comment) : that.comment == null;
    }
    @Override
    public int hashCode() {
        int result = (int) (commentId ^ (commentId >>> 32));
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        return result;
    }
    @Override
    public String toString() {
        return "Comment: id: " + commentId +  "commentId-" + userId + " userId-" + userId + " comment-" + comment;
    }

}
