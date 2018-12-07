package com.kozitski.pufar.entity.message;


import java.sql.Time;
import java.util.Objects;

public class UserMessage {
    private String message;
    private Time time;
    private String senderLogin;
    private String receiverLogin;

    public UserMessage() {
    }
    public UserMessage(String message,Time time, String senderLogin, String receiverLogin) {
        this.message = message;
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
    public Time getTime() {
        return time;
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