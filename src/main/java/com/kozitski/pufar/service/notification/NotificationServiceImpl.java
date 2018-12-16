package com.kozitski.pufar.service.notification;

import com.kozitski.pufar.dao.notification.NotificationDao;
import com.kozitski.pufar.dao.notification.NotificationDaoImpl;
import com.kozitski.pufar.entity.comment.NotificationComment;
import com.kozitski.pufar.entity.notification.Notification;
import com.kozitski.pufar.entity.notification.NotificationParameter;
import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.entity.user.UserStatus;

import java.util.ArrayList;

public class NotificationServiceImpl implements NotificationService {
    private NotificationDao notificationDao =  new NotificationDaoImpl();

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
    public boolean changeNotificationMessage(long notificationId,String newMessage, User currentUser) {
        boolean result = false;

        if(currentUser.getStatus().equals(UserStatus.ADMIN) || currentUser.getStatus().equals(UserStatus.SUPER_ADMIN)){
            result = notificationDao.changeNotificationMessage(notificationId, newMessage);
        }

        return result;
    }


}
