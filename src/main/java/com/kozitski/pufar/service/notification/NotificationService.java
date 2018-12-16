package com.kozitski.pufar.service.notification;

import com.kozitski.pufar.entity.comment.NotificationComment;
import com.kozitski.pufar.entity.notification.Notification;
import com.kozitski.pufar.entity.notification.NotificationParameter;
import com.kozitski.pufar.entity.user.User;

import java.util.ArrayList;

public interface NotificationService {

    ArrayList<Notification> searchTopNotificationsWithLimit(int limit);
    ArrayList<Notification> searchNotificationByParameters(NotificationParameter parameters);
    ArrayList<NotificationComment> searchCommentByNotificationId(long notificationId);
    boolean dropCommentById(long commentId, User currentUser);
    boolean dropNotificationById(long notificationId, User currentUser);
    boolean changeNotificationMessage(long notificationId, String newMessage, User currentUser);

}
