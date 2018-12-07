package com.kozitski.pufar.entity.notification;

import java.sql.Time;

public class Notification {
    private long notificationId;
    private String message;
    private UnitType unit;
    private double price;
    private long userId;
    private Time time;
    private double rate;

    public Notification() {
    }
    public Notification(long notificationId, String message, UnitType unit, double price, long userId, Time time) {
        this.notificationId = notificationId;
        this.message = message;
        this.unit = unit;
        this.price = price;
        this.userId = userId;
        this.time = time;
    }

    public long getNotificationId() {
        return notificationId;
    }
    public void setNotificationId(long notificationId) {
        this.notificationId = notificationId;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public UnitType getUnit() {
        return unit;
    }
    public void setUnit(UnitType unit) {
        this.unit = unit;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public long getUserId() {
        return userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }
    public Time getTime() {
        return time;
    }
    public void setTime(Time time) {
        this.time = time;
    }
    public double getRate() {
        return rate;
    }
    public void setRate(double rate) {
        this.rate = rate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Notification)) return false;

        Notification that = (Notification) o;

        if (getNotificationId() != that.getNotificationId()) return false;
        if (Double.compare(that.getPrice(), getPrice()) != 0) return false;
        if (getUserId() != that.getUserId()) return false;
        if (getMessage() != null ? !getMessage().equals(that.getMessage()) : that.getMessage() != null) return false;
        if (getUnit() != that.getUnit()) return false;
        return getTime() != null ? getTime().equals(that.getTime()) : that.getTime() == null;
    }
    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (getNotificationId() ^ (getNotificationId() >>> 32));
        result = 31 * result + (getMessage() != null ? getMessage().hashCode() : 0);
        result = 31 * result + (getUnit() != null ? getUnit().hashCode() : 0);
        temp = Double.doubleToLongBits(getPrice());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (int) (getUserId() ^ (getUserId() >>> 32));
        result = 31 * result + (getTime() != null ? getTime().hashCode() : 0);
        return result;
    }
    @Override
    public String toString() {
        return "Notification: Id-" + notificationId +  "message-" + message + " price-" + price +
                " userId-" + userId + " time-" + time + " rate-" + rate;
    }

}
