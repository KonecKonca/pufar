package com.kozitski.pufar.service.notification;

import com.kozitski.pufar.dao.notification.NotificationDao;
import com.kozitski.pufar.dao.notification.NotificationDaoImpl;
import com.kozitski.pufar.entity.notification.Notification;
import com.kozitski.pufar.entity.notification.NotificationParameter;

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


}
