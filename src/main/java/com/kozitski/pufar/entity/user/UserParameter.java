package com.kozitski.pufar.entity.user;

// TODO: Auto-generated Javadoc
/**
 * The Class UserParameter.
 */
public class UserParameter {
    
    /** The user id. */
    private Long userId;
    
    /** The login start. */
    private String loginStart;
    
    /** The status. */
    private UserStatus status;

    /**
     * Instantiates a new user parameter.
     */
    public UserParameter() {
    }
    
    /**
     * Instantiates a new user parameter.
     *
     * @param userId the user id
     * @param loginStart the login start
     * @param status the status
     */
    public UserParameter(long userId, String loginStart, UserStatus status) {
        this.userId = userId;
        this.loginStart = loginStart;
        this.status = status;
    }

    /**
     * Gets the user id.
     *
     * @return the user id
     */
    public Long getUserId() {
        return userId;
    }
    
    /**
     * Sets the user id.
     *
     * @param userId the new user id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    /**
     * Gets the login start.
     *
     * @return the login start
     */
    public String getLoginStart() {
        return loginStart;
    }
    
    /**
     * Sets the login start.
     *
     * @param loginStart the new login start
     */
    public void setLoginStart(String loginStart) {
        this.loginStart = loginStart;
    }
    
    /**
     * Gets the status.
     *
     * @return the status
     */
    public UserStatus getStatus() {
        return status;
    }
    
    /**
     * Sets the status.
     *
     * @param status the new status
     */
    public void setStatus(UserStatus status) {
        this.status = status;
    }

    /**
     * To string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "UserParameter{" +
                "userId=" + userId +
                ", loginStart='" + loginStart + '\'' +
                ", status=" + status +
                '}';
    }
}
