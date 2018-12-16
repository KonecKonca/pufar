package com.kozitski.pufar.dao.notification;

import com.kozitski.pufar.dao.PufarDao;
import com.kozitski.pufar.entity.comment.NotificationComment;
import com.kozitski.pufar.entity.notification.Notification;
import com.kozitski.pufar.entity.notification.NotificationParameter;
import com.kozitski.pufar.entity.user.User;

import java.util.ArrayList;

public interface NotificationDao extends PufarDao<Notification, NotificationParameter> {

    ArrayList<Notification> searchTopNotificationsWithLimit(int limit);
    ArrayList<Notification> searchByParameters(NotificationParameter parameters);

    ArrayList<NotificationComment> searchCommentByNotificationId(long notificationId);
    boolean dropCommentById(long commentId);
    boolean dropNotificationById(long notificationId);
    boolean changeNotificationMessage(long notificationId, String newMessage);

}
