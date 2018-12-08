package com.kozitski.pufar.entity.message;

import java.sql.Time;
import java.util.Date;
import java.util.Objects;

public class UserMessage {
    private String message;
    private Date date;
    private Time time;
    private String senderLogin;
    private String receiverLogin;

    public UserMessage() {
    }
    public UserMessage(String message, Date date, Time time, String senderLogin, String receiverLogin) {
        this.message = message;
        this.date = date;
        this.time = time;
        this.senderLogin = senderLogin;
        this.receiverLogin = receiverLogin;
    }
    public String getMessage() {
        return message;
    }
    public String getSenderLogin() {
        return senderLogin;
    }
    public String getReceiverLogin() {
        return receiverLogin;
    }
    public Date getDate() {
        return date;
    }
    public Time getTime() {
        return time;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public void setSenderLogin(String senderLogin) {
        this.senderLogin = senderLogin;
    }
    public void setReceiverLogin(String receiverLogin) {
        this.receiverLogin = receiverLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserMessage)) return false;
        UserMessage that = (UserMessage) o;
        return Objects.equals(message, that.message) &&
                Objects.equals(senderLogin, that.senderLogin) &&
                Objects.equals(receiverLogin, that.receiverLogin);
    }
    @Override
    public int hashCode() {
        return Objects.hash(message, senderLogin, receiverLogin);
    }
    @Override
    public String toString() {
        return "Message: from (" + senderLogin + "), to" + "(" + receiverLogin + "), content: " + message;
    }

}