package com.kozitski.pufar.entity.notification;

public class NotificationParameter {
    private Long notificationId;
    private Double lowerPrice;
    private Double higherPrice;

    private Integer passedTime;
    private Long senderId;
    private UnitType unitType;

    private Double lowerRate;
    private Double higherRate;

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
    public NotificationParameter() {
    }

    public Long getNotificationId() {
        return notificationId;
    }
    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }
    public Double getLowerPrice() {
        return lowerPrice;
    }
    public void setLowerPrice(Double lowerPrice) {
        this.lowerPrice = lowerPrice;
    }
    public Double getHigherPrice() {
        return higherPrice;
    }
    public void setHigherPrice(Double higherPrice) {
        this.higherPrice = higherPrice;
    }
    public Integer getPassedTime() {
        return passedTime;
    }
    public void setPassedTime(Integer passedTime) {
        this.passedTime = passedTime;
    }
    public Long getSenderId() {
        return senderId;
    }
    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }
    public UnitType getUnitType() {
        return unitType;
    }
    public void setUnitType(UnitType unitType) {
        this.unitType = unitType;
    }
    public Double getLowerRate() {
        return lowerRate;
    }
    public void setLowerRate(Double lowerRate) {
        this.lowerRate = lowerRate;
    }
    public Double getHigherRate() {
        return higherRate;
    }
    public void setHigherRate(Double higherRate) {
        this.higherRate = higherRate;
    }

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
