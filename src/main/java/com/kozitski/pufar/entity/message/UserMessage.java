package com.kozitski.pufar.entity.message;

import com.kozitski.pufar.util.CommonConstant;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

// TODO: Auto-generated Javadoc
/**
 * The Class UserMessage.
 */
public class UserMessage {
    
    /** The message. */
    private String message;
    
    /** The date. */
    private Date date;
    
    /** The sender login. */
    private String senderLogin;
    
    /** The receiver login. */
    private String receiverLogin;

    /**
     * Instantiates a new user message.
     */
    public UserMessage() {
    }
    
    /**
     * Instantiates a new user message.
     *
     * @param message the message
     * @param date the date
     * @param senderLogin the sender login
     * @param receiverLogin the receiver login
     */
    public UserMessage(String message, Date date, String senderLogin, String receiverLogin) {
        this.message = message;
        this.date = date;
        this.senderLogin = senderLogin;
        this.receiverLogin = receiverLogin;
    }
    
    /**
     * Gets the message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
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
     * Gets the receiver login.
     *
     * @return the receiver login
     */
    public String getReceiverLogin() {
        return receiverLogin;
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
     * Sets the message.
     *
     * @param message the new message
     */
    public void setMessage(String message) {
        if(message != null){
            this.message = message.trim();
        }
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
     * Sets the receiver login.
     *
     * @param receiverLogin the new receiver login
     */
    public void setReceiverLogin(String receiverLogin) {
        this.receiverLogin = receiverLogin;
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
     * Gets the string date.
     *
     * @return the string date
     */
    public String getStringDate(){
        return date == null ? null : new SimpleDateFormat(CommonConstant.DATE_TIME_COMMON_PATTERN).format(date);
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
        if (!(o instanceof UserMessage)) return false;
        UserMessage that = (UserMessage) o;
        return Objects.equals(message, that.message) &&
                Objects.equals(senderLogin, that.senderLogin) &&
                Objects.equals(receiverLogin, that.receiverLogin);
    }
    
    /**
     * Hash code.
     *
     * @return the int
     */
    @Override
    public int hashCode() {
        return Objects.hash(message, senderLogin, receiverLogin);
    }
    
    /**
     * To string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "Message: from (" + senderLogin + "), to" + "(" + receiverLogin + "), content: " + message + " Date-" + getStringDate();
    }


}