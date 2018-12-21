package com.kozitski.pufar.entity.notification;

import com.kozitski.pufar.entity.Imagenable;
import com.kozitski.pufar.entity.comment.NotificationComment;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class Notification implements Imagenable {
    private long notificationId;
    private String message;
    private UnitType unit;
    private double price;
    private long userId;
    private Date date;
    private BufferedImage image;
    private double rate;
    private ArrayList<NotificationComment> comments;

    public Notification() {
    }
    public Notification(long notificationId, String message, UnitType unit, double price, long userId, Date date, BufferedImage image, ArrayList<NotificationComment> comments) {
        this.notificationId = notificationId;
        this.message = message;
        this.unit = unit;
        this.price = price;
        this.userId = userId;
        this.date = date;
        this.comments = new ArrayList<>(comments);
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
    public double getRate() {
        return rate;
    }
    public void setRate(double rate) {
        this.rate = rate;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    @Override
    public BufferedImage getImage() {
        return image;
    }
    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public ArrayList<NotificationComment> getComments() {
        return new ArrayList<>(comments);
    }
    public void setComments(ArrayList<NotificationComment> comments) {
        this.comments = new ArrayList<>(comments);
    }

    @Override
    public String toString() {
        return "Notification: Id-" + notificationId +  " message-" + message + " price-" + price + " unit-" + unit +
                " userId-" + userId + " date-" + date + " rate-" + rate + " image-" + image + "|| COMMENTS: " + comments;
    }

}
