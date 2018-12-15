package com.kozitski.pufar.entity.user;

public class UserParameter {
    private Long userId;
    private String loginStart;
    private UserStatus status;

    public UserParameter() {
    }
    public UserParameter(long userId, String loginStart, UserStatus status) {
        this.userId = userId;
        this.loginStart = loginStart;
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getLoginStart() {
        return loginStart;
    }
    public void setLoginStart(String loginStart) {
        this.loginStart = loginStart;
    }
    public UserStatus getStatus() {
        return status;
    }
    public void setStatus(UserStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "UserParameter{" +
                "userId=" + userId +
                ", loginStart='" + loginStart + '\'' +
                ", status=" + status +
                '}';
    }
}
