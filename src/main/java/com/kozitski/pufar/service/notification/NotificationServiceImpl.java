package com.kozitski.pufar.service.notification;

import com.kozitski.pufar.command.RequestValue;
import com.kozitski.pufar.controller.LogoutController;
import com.kozitski.pufar.dao.notification.NotificationDao;
import com.kozitski.pufar.dao.notification.NotificationDaoImpl;
import com.kozitski.pufar.entity.comment.NotificationComment;
import com.kozitski.pufar.entity.notification.Notification;
import com.kozitski.pufar.entity.notification.NotificationParameter;
import com.kozitski.pufar.entity.notification.UnitType;
import com.kozitski.pufar.entity.number.MobilPhoneNumber;
import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.entity.user.UserStatus;
import com.kozitski.pufar.exception.PufarDAOException;
import com.kozitski.pufar.exception.PufarServiceException;
import com.kozitski.pufar.service.AbstractService;
import com.kozitski.pufar.service.InjectDao;
import com.kozitski.pufar.util.CommonConstant;
import com.kozitski.pufar.util.cursor.NotificationsCursor;
import com.kozitski.pufar.validation.annotation.primitive.integer.IntValid;
import com.kozitski.pufar.validation.annotation.primitive.string.StringValid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class NotificationServiceImpl extends AbstractService implements NotificationService {
    private static Logger LOGGER = LoggerFactory.getLogger(NotificationServiceImpl.class);

    @InjectDao
    private NotificationDao notificationDao;

    @Override
    public ArrayList<Notification> searchTopNotificationsWithLimit(int limit) {
        return notificationDao.searchTopNotificationsWithLimit(limit);
    }
    @Override
    public ArrayList<Notification> searchNotificationByParameters(NotificationParameter parameters) {
        return notificationDao.searchByParameters(parameters);
    }

    @Override
    public ArrayList<NotificationComment> searchCommentByNotificationId(long notificationId) {
        return notificationDao.searchCommentByNotificationId(notificationId);
    }
    @Override
    public boolean dropCommentById(long commentId, User currentUser) {
        boolean result = false;

        if(currentUser.getStatus().equals(UserStatus.ADMIN) || currentUser.getStatus().equals(UserStatus.SUPER_ADMIN)){
            result = notificationDao.dropCommentById(commentId);
        }

        return result;
    }
    @Override
    public boolean dropNotificationById(long notificationId, User currentUser) {
        boolean result = false;

        if(currentUser.getStatus().equals(UserStatus.ADMIN) || currentUser.getStatus().equals(UserStatus.SUPER_ADMIN)){
            result = notificationDao.dropNotificationById(notificationId);
        }

        return result;
    }
    @Override
    public boolean dropMyselfNotificationById(long notificationId){
        return notificationDao.dropNotificationById(notificationId);
    }

    @Override
    public boolean changeNotificationMessage(long notificationId,String newMessage, User currentUser) {
        boolean result = false;

        if(currentUser.getStatus().equals(UserStatus.ADMIN) || currentUser.getStatus().equals(UserStatus.SUPER_ADMIN)){
            result = notificationDao.changeNotificationMessage(notificationId, newMessage);
        }

        return result;
    }
    @Override
    public void addNotification(Notification notification) throws PufarServiceException {
        try {
            notificationDao.addNotification(notification);
        }
        catch (PufarDAOException e) {
            throw new PufarServiceException(e);
        }
    }
    @Override
    public ArrayList<Notification> searchNotificationsWithChangingCursor(RequestValue requestValue, UnitType unitType, int stepValue){
        ArrayList<Notification> result;

        requestValue.servletSessionPut(CommonConstant.NOTIFICATIONS_LAST_UNIT, unitType);
        NotificationsCursor cursor = (NotificationsCursor) requestValue.getAttribute(CommonConstant.NOTIFICATIONS_CURSOR);
        if(cursor == null){
            cursor = new NotificationsCursor();
            requestValue.servletSessionPut(CommonConstant.NOTIFICATIONS_CURSOR, cursor);
        }

        if(stepValue == 0){
            result = notificationDao.searchNotificationsByUnit(unitType, stepValue, CommonConstant.HOW_MUCH_NOTIFICATIONS);
        }
        else {
            boolean character = true;
            if(stepValue <= 0){
                character = false;
            }

            long notificationsNumber = notificationDao.searchNotificationsByUnitNumber(unitType);
            cursor.setMaxCursorValue((int) notificationsNumber);

            int limitStartRange = cursor.setCursor(unitType, character);
            result = notificationDao.searchNotificationsByUnit(unitType, limitStartRange, Math.abs(stepValue));
        }

        return result;
    }
    @Override
    public long sentComment(@StringValid String comment, long senderId, long notificationId) throws PufarServiceException{
        try {
            return notificationDao.addComment(comment, senderId, notificationId);
        }
        catch (PufarDAOException e) {
            throw new PufarServiceException(e);
        }
    }
    @Override
    @SuppressWarnings("unchecked")
    public void putMark(RequestValue requestValue, int mark, long senderId, long notificationId){
        try {
            double newRate = notificationDao.putMark(mark, senderId, notificationId);

            Notification handledNotification;
            ArrayList<Notification> notifications = (ArrayList<Notification>) requestValue.getAttribute(CommonConstant.CURRENT_NOTIFICATIONS);
            for(Notification notification : notifications){
                if(notification.getNotificationId() == notificationId){

                    handledNotification = notification;
                    handledNotification.setRate(newRate);
                    break;

                }
            }

        }
        catch (PufarDAOException e) {
            LOGGER.warn("Mark wasn't put", e);
        }
    }
    @Override
    public ArrayList<Notification> searchAllNotificationsByAuthorId(long authorIdw){
        return notificationDao.searchAllNotificationsByAuthorId(authorIdw);
    }



}
