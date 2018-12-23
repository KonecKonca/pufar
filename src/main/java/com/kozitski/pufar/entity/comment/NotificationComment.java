package com.kozitski.pufar.entity.comment;

import java.util.Date;

public class NotificationComment {
    private long commentId;
    private String senderLogin;
    private String comment;
    private Date date;

    public NotificationComment() {
    }
    public NotificationComment(long commentId, String senderLogin, String comment, Date date) {
        this.commentId = commentId;
        this.senderLogin = senderLogin;
        this.comment = comment;
        this.date = date;
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
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NotificationComment)) return false;

        NotificationComment comment1 = (NotificationComment) o;

        if (getCommentId() != comment1.getCommentId()) return false;
        if (getSenderLogin() != null ? !getSenderLogin().equals(comment1.getSenderLogin()) : comment1.getSenderLogin() != null)
            return false;
        if (getComment() != null ? !getComment().equals(comment1.getComment()) : comment1.getComment() != null)
            return false;
        return getDate() != null ? getDate().equals(comment1.getDate()) : comment1.getDate() == null;
    }
    @Override
    public int hashCode() {
        int result = (int) (getCommentId() ^ (getCommentId() >>> 32));
        result = 31 * result + (getSenderLogin() != null ? getSenderLogin().hashCode() : 0);
        result = 31 * result + (getComment() != null ? getComment().hashCode() : 0);
        result = 31 * result + (getDate() != null ? getDate().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Comment: id: " + commentId +  "commentId-" + senderLogin + " senderLogin-" + senderLogin +
                " comment-" + comment + " date-" + date;
    }

}
