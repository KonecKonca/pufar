package com.kozitski.pufar.entity.user;

import java.util.Objects;

public class User {
    private long userId;
    private String login;
    private String password;
    private UserStatus status;

    public User() {
    }
    public User(long userId, String login, String password, UserStatus status) {
        this.userId = userId;
        this.login = login;
        this.password = password;
        this.status = status;
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
        return "UserServiceImpl: " + "id-" + userId + " login-" + login + " status-" + status;
    }

}
