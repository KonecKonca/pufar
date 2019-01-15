package com.kozitski.pufar.entity.notification;

/**
 * The Class NotificationParameter.
 * Developed for searching notifications according
 * with some rules
 */
public class NotificationParameter {
    
    /** The notification id. */
    private Long notificationId;
    
    /** The lower price. */
    private Double lowerPrice;
    
    /** The higher price. */
    private Double higherPrice;

    /** The passed time. */
    private Integer passedTime;
    
    /** The sender id. */
    private Long senderId;
    
    /** The unit type. */
    private UnitType unitType;

    /** The lower rate. */
    private Double lowerRate;
    
    /** The higher rate. */
    private Double higherRate;

    /**
     * Instantiates a new notification parameter.
     *
     * @param notificationId the notification id
     * @param lowerPrice the lower price
     * @param higherPrice the higher price
     * @param passedTime the passed time
     * @param senderId the sender id
     * @param unitType the unit type
     * @param lowerRate the lower rate
     * @param higherRate the higher rate
     */
    public NotificationParameter(long notificationId, double lowerPrice, double higherPrice,
                                 int passedTime, long senderId, UnitType unitType, double lowerRate, double higherRate) {
        this.notificationId = notificationId;
        this.lowerPrice = lowerPrice;
        this.higherPrice = higherPrice;
        this.passedTime = passedTime;
        this.senderId = senderId;
        this.unitType = unitType;
        this.lowerRate = lowerRate;
        this.higherRate = higherRate;
    }
    
    /**
     * Instantiates a new notification parameter.
     */
    public NotificationParameter() {
    }

    /**
     * Gets the notification id.
     *
     * @return the notification id
     */
    public Long getNotificationId() {
        return notificationId;
    }
    
    /**
     * Sets the notification id.
     *
     * @param notificationId the new notification id
     */
    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }
    
    /**
     * Gets the lower price.
     *
     * @return the lower price
     */
    public Double getLowerPrice() {
        return lowerPrice;
    }
    
    /**
     * Sets the lower price.
     *
     * @param lowerPrice the new lower price
     */
    public void setLowerPrice(Double lowerPrice) {
        this.lowerPrice = lowerPrice;
    }
    
    /**
     * Gets the higher price.
     *
     * @return the higher price
     */
    public Double getHigherPrice() {
        return higherPrice;
    }
    
    /**
     * Sets the higher price.
     *
     * @param higherPrice the new higher price
     */
    public void setHigherPrice(Double higherPrice) {
        this.higherPrice = higherPrice;
    }
    
    /**
     * Gets the passed time.
     *
     * @return the passed time
     */
    public Integer getPassedTime() {
        return passedTime;
    }
    
    /**
     * Sets the passed time.
     *
     * @param passedTime the new passed time
     */
    public void setPassedTime(Integer passedTime) {
        this.passedTime = passedTime;
    }
    
    /**
     * Gets the sender id.
     *
     * @return the sender id
     */
    public Long getSenderId() {
        return senderId;
    }
    
    /**
     * Sets the sender id.
     *
     * @param senderId the new sender id
     */
    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }
    
    /**
     * Gets the unit type.
     *
     * @return the unit type
     */
    public UnitType getUnitType() {
        return unitType;
    }
    
    /**
     * Sets the unit type.
     *
     * @param unitType the new unit type
     */
    public void setUnitType(UnitType unitType) {
        this.unitType = unitType;
    }
    
    /**
     * Gets the lower rate.
     *
     * @return the lower rate
     */
    public Double getLowerRate() {
        return lowerRate;
    }
    
    /**
     * Sets the lower rate.
     *
     * @param lowerRate the new lower rate
     */
    public void setLowerRate(Double lowerRate) {
        this.lowerRate = lowerRate;
    }
    
    /**
     * Gets the higher rate.
     *
     * @return the higher rate
     */
    public Double getHigherRate() {
        return higherRate;
    }
    
    /**
     * Sets the higher rate.
     *
     * @param higherRate the new higher rate
     */
    public void setHigherRate(Double higherRate) {
        this.higherRate = higherRate;
    }

    /**
     * To string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "NotificationParameter{" +
                "notificationId=" + notificationId +
                ", lowerPrice=" + lowerPrice +
                ", higherPrice=" + higherPrice +
                ", passedTime=" + passedTime +
                ", senderId=" + senderId +
                ", unitType=" + unitType +
                ", lowerRate=" + lowerRate +
                ", higherRate=" + higherRate +
                '}';
    }
}
