package com.kozitski.pufar.service.notification;

import com.kozitski.pufar.dao.notification.NotificationDao;
import com.kozitski.pufar.dao.notification.NotificationDaoImpl;
import com.kozitski.pufar.entity.notification.Notification;

import java.util.ArrayList;

public class NotificationServiceImpl implements NotificationService {
    private NotificationDao notificationDao =  new NotificationDaoImpl();

    @Override
    public ArrayList<Notification> searchTopNotificationsWithLimit(int limit) {

        return notificationDao.searchTopNotificationsWithLimit(limit);

    }

}
