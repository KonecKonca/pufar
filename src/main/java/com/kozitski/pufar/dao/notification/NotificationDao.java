package com.kozitski.pufar.dao.notification;

import com.kozitski.pufar.dao.PufarDao;
import com.kozitski.pufar.entity.comment.NotificationComment;
import com.kozitski.pufar.entity.notification.Notification;
import com.kozitski.pufar.entity.notification.NotificationParameter;
import com.kozitski.pufar.entity.notification.UnitType;
import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.exception.PufarDAOException;
import com.kozitski.pufar.validation.annotation.notification.NotificationValid;
import com.kozitski.pufar.validation.annotation.primitive.string.StringValid;

import java.util.ArrayList;

public interface NotificationDao extends PufarDao<Notification, NotificationParameter> {

    ArrayList<Notification> searchTopNotificationsWithLimit(int limit);
    ArrayList<Notification> searchByParameters(NotificationParameter parameters);

    ArrayList<NotificationComment> searchCommentByNotificationId(long notificationId);
    boolean dropCommentById(long commentId);
    boolean dropNotificationById(long notificationId);
    boolean changeNotificationMessage(long notificationId, String newMessage);
    void addNotification(Notification notification) throws PufarDAOException;
    long searchNotificationsByUnitNumber(UnitType unitType);
    ArrayList<Notification> searchNotificationsByUnit(UnitType unitType, int limitStart, int limitStep);

    long addComment(String comment, long senderId, long notificationId) throws PufarDAOException;
    double putMark(int mark, long senderId, long notificationId) throws PufarDAOException;

}
