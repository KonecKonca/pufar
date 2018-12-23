package com.kozitski.pufar.entity.user;

import com.kozitski.pufar.entity.number.MobilPhoneNumber;

import java.util.Objects;

public class User {
    private long userId;
    private String login;
    private String password;
    private UserStatus status;
    private boolean isBanned;
    private MobilPhoneNumber number;

    public User() {
    }
    public User(long userId, String login, String password, UserStatus status, boolean isBanned, MobilPhoneNumber number) {
        this.userId = userId;
        this.login = login;
        this.password = password;
        this.status = status;
        this.isBanned = isBanned;
        this.number = number;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }
    public void setBanned(boolean banned) {
        isBanned = banned;
    }
    public void setNumber(MobilPhoneNumber number) {
        this.number = number;
    }

    public long getUserId() {
        return userId;
    }
    public String getLogin() {
        return login;
    }
    public String getPassword() {
        return password;
    }
    public UserStatus getStatus() {
        return status;
    }
    public boolean isBanned() {
        return isBanned;
    }
    public MobilPhoneNumber getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getUserId() == user.getUserId() &&
                Objects.equals(getLogin(), user.getLogin()) &&
                Objects.equals(getPassword(), user.getPassword()) &&
                getStatus() == user.getStatus();
    }
    @Override
    public int hashCode() {
        return Objects.hash(getUserId());
    }
    @Override
    public String toString() {
        return "UserServiceImpl: " + "id-" + userId + " login-" + login + " status-" + status +
                " isBanned-" + isBanned + " number-" + number;
    }

}
