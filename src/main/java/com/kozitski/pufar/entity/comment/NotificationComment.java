package com.kozitski.pufar.entity.comment;

public class NotificationComment {
    private long commentId;
    private String senderLogin;
    private String comment;

    public NotificationComment() {
    }
    public NotificationComment(long commentId, String senderLogin, String comment) {
        this.commentId = commentId;
        this.senderLogin = senderLogin;
        this.comment = comment;
    }

    public long getCommentId() {
        return commentId;
    }
    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }
    public String getSenderLogin() {
        return senderLogin;
    }
    public void setSenderLogin(String senderLogin) {
        this.senderLogin = senderLogin;
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

        if (getCommentId() != that.getCommentId()) return false;
        if (getSenderLogin() != null ? !getSenderLogin().equals(that.getSenderLogin()) : that.getSenderLogin() != null) return false;
        return getComment() != null ? getComment().equals(that.getComment()) : that.getComment() == null;
    }
    @Override
    public int hashCode() {
        int result = (int) (getCommentId() ^ (getCommentId() >>> 32));
        result = 31 * result + (getSenderLogin() != null ? getSenderLogin().hashCode() : 0);
        result = 31 * result + (getComment() != null ? getComment().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Comment: id: " + commentId +  "commentId-" + senderLogin + " senderLogin-" + senderLogin + " comment-" + comment;
    }

}
