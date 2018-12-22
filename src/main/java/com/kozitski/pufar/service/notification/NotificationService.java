package com.kozitski.pufar.service.notification;

import com.kozitski.pufar.command.RequestValue;
import com.kozitski.pufar.entity.comment.NotificationComment;
import com.kozitski.pufar.entity.notification.Notification;
import com.kozitski.pufar.entity.notification.NotificationParameter;
import com.kozitski.pufar.entity.notification.UnitType;
import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.exception.PufarServiceException;
import com.kozitski.pufar.validation.annotation.AspectValid;
import com.kozitski.pufar.validation.annotation.notification.NotificationValid;

import java.util.ArrayList;

public interface NotificationService {

    ArrayList<Notification> searchTopNotificationsWithLimit(int limit);
    ArrayList<Notification> searchNotificationByParameters(NotificationParameter parameters);
    ArrayList<NotificationComment> searchCommentByNotificationId(long notificationId);
    boolean dropCommentById(long commentId, User currentUser);
    boolean dropNotificationById(long notificationId, User currentUser);
    boolean changeNotificationMessage(long notificationId, String newMessage, User currentUser);
    @AspectValid
    void addNotification(@NotificationValid(minRate = 0, stringPattern = ".*") Notification notification) throws PufarServiceException;
    ArrayList<Notification> searchNotificationsWithChangingCursor(RequestValue requestValue, UnitType unitType, int stepValue);

}
