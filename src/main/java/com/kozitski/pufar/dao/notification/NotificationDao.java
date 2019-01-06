package com.kozitski.pufar.dao.notification;

import com.kozitski.pufar.dao.PufarDao;
import com.kozitski.pufar.entity.comment.NotificationComment;
import com.kozitski.pufar.entity.notification.Notification;
import com.kozitski.pufar.entity.notification.NotificationParameter;
import com.kozitski.pufar.entity.notification.UnitType;
import com.kozitski.pufar.exception.PufarDAOException;

import java.util.List;

public interface NotificationDao extends PufarDao<Notification, NotificationParameter> {

    List<Notification> searchTopNotificationsWithLimit(int limit);

    List<Notification> searchByParameters(NotificationParameter parameters);

    List<NotificationComment> searchCommentByNotificationId(long notificationId);

    boolean dropCommentById(long commentId);

    boolean dropNotificationById(long notificationId);

    boolean changeNotificationMessage(long notificationId, String newMessage);

    void addNotification(Notification notification) throws PufarDAOException;

    long searchNotificationsByUnitNumber(UnitType unitType);

    List<Notification> searchNotificationsByUnit(UnitType unitType, int limitStart, int limitStep);

    long addComment(String comment, long senderId, long notificationId) throws PufarDAOException;

    double putMark(int mark, long senderId, long notificationId) throws PufarDAOException;

    List<Notification> searchAllNotificationsByAuthorId(long authorIdw);

}
