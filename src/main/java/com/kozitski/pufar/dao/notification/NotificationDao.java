package com.kozitski.pufar.dao.notification;

import com.kozitski.pufar.dao.PufarDao;
import com.kozitski.pufar.entity.comment.NotificationComment;
import com.kozitski.pufar.entity.notification.Notification;
import com.kozitski.pufar.entity.notification.NotificationParameter;
import com.kozitski.pufar.entity.notification.UnitType;
import com.kozitski.pufar.exception.PufarDAOException;

import java.util.List;

/**
 * The Interface NotificationDao.
 */
public interface NotificationDao extends PufarDao<Notification, NotificationParameter> {

    /**
     * Search top notifications with limit.
     *
     * @param limit the limit
     * @return the list
     */
    List<Notification> searchTopNotificationsWithLimit(int limit);

    /**
     * Search by parameters.
     *
     * @param parameters the parameters
     * @return the list
     */
    List<Notification> searchByParameters(NotificationParameter parameters);

    /**
     * Search comment by notification id.
     *
     * @param notificationId the notification id
     * @return the list
     */
    List<NotificationComment> searchCommentByNotificationId(long notificationId);

    /**
     * Drop comment by id.
     *
     * @param commentId the comment id
     * @return true, if successful
     */
    boolean dropCommentById(long commentId);

    /**
     * Drop notification by id.
     *
     * @param notificationId the notification id
     * @return true, if successful
     */
    boolean dropNotificationById(long notificationId);

    /**
     * Change notification message.
     *
     * @param notificationId the notification id
     * @param newMessage the new message
     * @return true, if successful
     */
    boolean changeNotificationMessage(long notificationId, String newMessage);

    /**
     * Adds the notification.
     *
     * @param notification the notification
     * @throws PufarDAOException the pufar DAO exception
     */
    void addNotification(Notification notification) throws PufarDAOException;

    /**
     * Search notifications by unit number.
     *
     * @param unitType the unit type
     * @return the long
     */
    long searchNotificationsByUnitNumber(UnitType unitType);

    /**
     * Search notifications by unit.
     *
     * @param unitType the unit type
     * @param limitStart the limit start
     * @param limitStep the limit step
     * @return the list
     */
    List<Notification> searchNotificationsByUnit(UnitType unitType, int limitStart, int limitStep);

    /**
     * Adds the comment.
     *
     * @param comment the comment
     * @param senderId the sender id
     * @param notificationId the notification id
     * @return the long
     * @throws PufarDAOException the pufar DAO exception
     */
    long addComment(String comment, long senderId, long notificationId) throws PufarDAOException;

    /**
     * Put mark.
     *
     * @param mark the mark
     * @param senderId the sender id
     * @param notificationId the notification id
     * @return the double
     * @throws PufarDAOException the pufar DAO exception
     */
    double putMark(int mark, long senderId, long notificationId) throws PufarDAOException;

    /**
     * Search all notifications by author id.
     *
     * @param authorIdw the author idw
     * @return the list
     */
    List<Notification> searchAllNotificationsByAuthorId(long authorIdw);

}
