package com.kozitski.pufar.entity.user;

import com.kozitski.pufar.entity.number.MobilPhoneNumber;

import java.util.Objects;

// TODO: Auto-generated Javadoc
/**
 * The Class User.
 */
public class User {
    
    /** The user id. */
    private long userId;
    
    /** The login. */
    private String login;
    
    /** The password. */
    private String password;
    
    /** The status. */
    private UserStatus status;
    
    /** The is banned. */
    private boolean isBanned;
    
    /** The number. */
    private MobilPhoneNumber number;

    /**
     * Instantiates a new user.
     */
    public User() {
    }
    
    /**
     * Instantiates a new user.
     *
     * @param userId the user id
     * @param login the login
     * @param password the password
     * @param status the status
     * @param isBanned the is banned
     * @param number the number
     */
    public User(long userId, String login, String password, UserStatus status, boolean isBanned, MobilPhoneNumber number) {
        this.userId = userId;
        this.login = login;
        this.password = password;
        this.status = status;
        this.isBanned = isBanned;
        this.number = number;
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
     * Sets the login.
     *
     * @param login the new login
     */
    public void setLogin(String login) {
        if(login != null){
            this.login = login.trim();
        }
    }
    
    /**
     * Sets the password.
     *
     * @param password the new password
     */
    public void setPassword(String password) {
        if(password != null){
            this.password = password.trim();
        }
    }
    
    /**
     * Sets the user id.
     *
     * @param userId the new user id
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }
    
    /**
     * Sets the banned.
     *
     * @param banned the new banned
     */
    public void setBanned(boolean banned) {
        isBanned = banned;
    }
    
    /**
     * Sets the number.
     *
     * @param number the new number
     */
    public void setNumber(MobilPhoneNumber number) {
        this.number = number;
    }

    /**
     * Gets the user id.
     *
     * @return the user id
     */
    public long getUserId() {
        return userId;
    }
    
    /**
     * Gets the login.
     *
     * @return the login
     */
    public String getLogin() {
        return login;
    }
    
    /**
     * Gets the password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
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
     * Checks if is banned.
     *
     * @return true, if is banned
     */
    public boolean isBanned() {
        return isBanned;
    }
    
    /**
     * Gets the number.
     *
     * @return the number
     */
    public MobilPhoneNumber getNumber() {
        return number;
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
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getUserId() == user.getUserId() &&
                Objects.equals(getLogin(), user.getLogin()) &&
                Objects.equals(getPassword(), user.getPassword()) &&
                getStatus() == user.getStatus();
    }
    
    /**
     * Hash code.
     *
     * @return the int
     */
    @Override
    public int hashCode() {
        return Objects.hash(getUserId());
    }
    
    /**
     * To string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "UserServiceImpl: " + "id-" + userId + " login-" + login + " status-" + status +
                " isBanned-" + isBanned + " number-" + number;
    }

}
