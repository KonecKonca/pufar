package com.kozitski.pufar.entity.comment;

import com.kozitski.pufar.util.CommonConstant;

import java.text.SimpleDateFormat;
import java.util.Date;

// TODO: Auto-generated Javadoc
/**
 * The Class NotificationComment.
 */
public class NotificationComment {
    
    /** The comment id. */
    private long commentId;
    
    /** The sender login. */
    private String senderLogin;
    
    /** The comment. */
    private String comment;
    
    /** The date. */
    private Date date;

    /**
     * Instantiates a new notification comment.
     */
    public NotificationComment() {
    }
    
    /**
     * Instantiates a new notification comment.
     *
     * @param commentId the comment id
     * @param senderLogin the sender login
     * @param comment the comment
     * @param date the date
     */
    public NotificationComment(long commentId, String senderLogin, String comment, Date date) {
        this.commentId = commentId;
        this.senderLogin = senderLogin;
        this.comment = comment;
        this.date = date;
    }

    /**
     * Gets the comment id.
     *
     * @return the comment id
     */
    public long getCommentId() {
        return commentId;
    }
    
    /**
     * Sets the comment id.
     *
     * @param commentId the new comment id
     */
    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }
    
    /**
     * Gets the sender login.
     *
     * @return the sender login
     */
    public String getSenderLogin() {
        return senderLogin;
    }
    
    /**
     * Sets the sender login.
     *
     * @param senderLogin the new sender login
     */
    public void setSenderLogin(String senderLogin) {
        this.senderLogin = senderLogin;
    }
    
    /**
     * Gets the comment.
     *
     * @return the comment
     */
    public String getComment() {
        return comment;
    }
    
    /**
     * Sets the comment.
     *
     * @param comment the new comment
     */
    public void setComment(String comment) {
        if(comment != null){
            this.comment = comment.trim();
        }
    }
    
    /**
     * Sets the date.
     *
     * @param date the new date
     */
    public void setDate(Date date) {
        this.date = date;
    }
    
    /**
     * Gets the date.
     *
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Gets the string date.
     *
     * @return the string date
     */
    public String getStringDate(){
        if(date != null){
            return new SimpleDateFormat(CommonConstant.DATE_TIME_COMMON_PATTERN).format(date);
        }
        else {
            return null;
        }
    }

    /**
     * Equals.
     *
     * @param o the o
     * @return true, if successful
     */
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
    
    /**
     * Hash code.
     *
     * @return the int
     */
    @Override
    public int hashCode() {
        int result = (int) (getCommentId() ^ (getCommentId() >>> 32));
        result = 31 * result + (getSenderLogin() != null ? getSenderLogin().hashCode() : 0);
        result = 31 * result + (getComment() != null ? getComment().hashCode() : 0);
        result = 31 * result + (getDate() != null ? getDate().hashCode() : 0);
        return result;
    }

    /**
     * To string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "Comment: id: " + commentId +  "commentId-" + senderLogin + " senderLogin-" + senderLogin +
                " comment-" + comment + " date-" + getStringDate();
    }

}
