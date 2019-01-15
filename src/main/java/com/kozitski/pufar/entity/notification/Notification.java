package com.kozitski.pufar.entity.notification;

import com.kozitski.pufar.entity.Imagenable;
import com.kozitski.pufar.entity.comment.NotificationComment;
import com.kozitski.pufar.util.CommonConstant;

import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class Notification.
 */
public class Notification implements Imagenable {
    
    /** The notification id. */
    private long notificationId;
    
    /** The message. */
    private String message;
    
    /** The unit. */
    private UnitType unit;
    
    /** The price. */
    private double price;
    
    /** The user id. */
    private long userId;
    
    /** The date. */
    private Date date;
    
    /** The image. */
    private BufferedImage image;
    
    /** The rate. */
    private double rate;
    
    /** The comments. */
    private List<NotificationComment> comments;

    /**
     * Instantiates a new notification.
     */
    public Notification() {
    }

    /**
     * Instantiates a new notification.
     *
     * @param notificationId the notification id
     * @param message the message
     * @param unit the unit
     * @param price the price
     * @param userId the user id
     * @param date the date
     * @param image the image
     * @param comments the comments
     */
    public Notification(long notificationId, String message, UnitType unit, double price, long userId, Date date, BufferedImage image, List<NotificationComment> comments) {
        this.notificationId = notificationId;
        this.message = message;
        this.unit = unit;
        this.price = price;
        this.userId = userId;
        this.date = date;
        this.image = image;
        if (comments != null) {
            this.comments = new ArrayList<>(comments);
        }
    }

    /**
     * Gets the notification id.
     *
     * @return the notification id
     */
    public long getNotificationId() {
        return notificationId;
    }

    /**
     * Sets the notification id.
     *
     * @param notificationId the new notification id
     */
    public void setNotificationId(long notificationId) {
        this.notificationId = notificationId;
    }

    /**
     * Gets the message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message.
     *
     * @param message the new message
     */
    public void setMessage(String message) {
        if (message != null) {
            this.message = message.trim();
        }
    }

    /**
     * Gets the unit.
     *
     * @return the unit
     */
    public UnitType getUnit() {
        return unit;
    }

    /**
     * Sets the unit.
     *
     * @param unit the new unit
     */
    public void setUnit(UnitType unit) {
        this.unit = unit;
    }

    /**
     * Gets the price.
     *
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price.
     *
     * @param price the new price
     */
    public void setPrice(double price) {
        this.price = price;
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
     * Sets the user id.
     *
     * @param userId the new user id
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * Gets the rate.
     *
     * @return the rate
     */
    public double getRate() {
        return rate;
    }

    /**
     * Sets the rate.
     *
     * @param rate the new rate
     */
    public void setRate(double rate) {
        this.rate = rate;
    }

    /**
     * Gets the date.
     *
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets the date.
     *
     * @param date the new date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Gets the image.
     *
     * @return the image
     */
    @Override
    public BufferedImage getImage() {
        return image;
    }

    /**
     * Sets the image.
     *
     * @param image the new image
     */
    public void setImage(BufferedImage image) {
        this.image = image;
    }

    /**
     * Gets the comments.
     *
     * @return the comments
     */
    public List<NotificationComment> getComments() {
        return new ArrayList<>(comments);
    }

    /**
     * Sets the comments.
     *
     * @param comments the new comments
     */
    public void setComments(List<NotificationComment> comments) {
        this.comments = new ArrayList<>(comments);
    }

    /**
     * Insert new comment.
     *
     * @param comment the comment
     */
    public void insertNewComment(NotificationComment comment) {
        comments.add(0, comment);
    }

    /**
     * Gets the string date.
     *
     * @return the string date
     */
    public String getStringDate() {
        return date == null ? null : new SimpleDateFormat(CommonConstant.DATE_TIME_COMMON_PATTERN).format(date);
    }

    /**
     * To string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "Notification: Id-" + notificationId + " message-" + message + " price-" + price + " unit-" + unit +
                " userId-" + userId + " date-" + getStringDate() + " rate-" + rate + "|| COMMENTS: " + comments;
    }

}
