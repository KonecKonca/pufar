package com.kozitski.pufar.service.notification;

import com.kozitski.pufar.command.RequestValue;
import com.kozitski.pufar.entity.comment.NotificationComment;
import com.kozitski.pufar.entity.notification.Notification;
import com.kozitski.pufar.entity.notification.NotificationParameter;
import com.kozitski.pufar.entity.notification.UnitType;
import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.exception.PufarServiceException;
import com.kozitski.pufar.exception.PufarValidationException;
import com.kozitski.pufar.validation.annotation.AspectValid;
import com.kozitski.pufar.validation.annotation.notification.NotificationValid;
import com.kozitski.pufar.validation.annotation.primitive.integer.IntValid;
import com.kozitski.pufar.validation.annotation.primitive.string.StringValid;

import java.util.List;

/**
 * The Interface NotificationService.
 */
public interface NotificationService {

    /**
     * Search top notifications with limit.
     *
     * @param limit the limit
     * @return the list
     */
    List<Notification> searchTopNotificationsWithLimit(int limit);

    /**
     * Search notification by parameters.
     *
     * @param parameters the parameters
     * @return the list
     */
    List<Notification> searchNotificationByParameters(NotificationParameter parameters);

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
     * @param currentUser the current user
     * @return true, if successful
     */
    boolean dropCommentById(long commentId, User currentUser);

    /**
     * Drop notification by id.
     *
     * @param notificationId the notification id
     * @param currentUser the current user
     * @return true, if successful
     */
    boolean dropNotificationById(long notificationId, User currentUser);

    /**
     * Drop myself notification by id.
     *
     * @param notificationId the notification id
     * @return true, if successful
     */
    boolean dropMyselfNotificationById(long notificationId);

    /**
     * Change notification message.
     *
     * @param notificationId the notification id
     * @param newMessage the new message
     * @param currentUser the current user
     * @return true, if successful
     */
    boolean changeNotificationMessage(long notificationId, String newMessage, User currentUser);

    /**
     * Adds the notification.
     *
     * @param notification the notification
     * @throws PufarServiceException the pufar service exception
     * @throws PufarValidationException the pufar validation exception
     */
    @AspectValid
    void addNotification(@NotificationValid Notification notification) throws PufarServiceException, PufarValidationException;

    /**
     * Search notifications with changing cursor.
     *
     * @param requestValue the request value
     * @param unitType the unit type
     * @param stepValue the step value
     * @return the list
     */
    List<Notification> searchNotificationsWithChangingCursor(RequestValue requestValue, UnitType unitType, int stepValue);

    /**
     * Sent comment.
     *
     * @param comment the comment
     * @param senderId the sender id
     * @param notificationId the notification id
     * @return the long
     * @throws PufarServiceException the pufar service exception
     * @throws PufarValidationException the pufar validation exception
     */
    @AspectValid
    long sentComment(@StringValid String comment, long senderId, long notificationId) throws PufarServiceException, PufarValidationException;

    /**
     * Put mark.
     *
     * @param requestValue the request value
     * @param mark the mark
     * @param senderId the sender id
     * @param notificationId the notification id
     * @throws PufarValidationException the pufar validation exception
     */
    @AspectValid
    void putMark(RequestValue requestValue, @IntValid(minValue = 1, maxValue = 5) int mark, long senderId, long notificationId) throws PufarValidationException;

    /**
     * Search all notifications by author id.
     *
     * @param authorIdw the author idw
     * @return the list
     */
    List<Notification> searchAllNotificationsByAuthorId(long authorIdw);

}
