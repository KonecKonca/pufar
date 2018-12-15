package com.kozitski.pufar.dao.notification;

import com.kozitski.pufar.dao.PufarDao;
import com.kozitski.pufar.entity.notification.Notification;
import com.kozitski.pufar.entity.notification.NotificationParameter;

import java.util.ArrayList;

public interface NotificationDao extends PufarDao<Notification, NotificationParameter> {

    ArrayList<Notification> searchTopNotificationsWithLimit(int limit);
    ArrayList<Notification> searchByParameters(NotificationParameter parameters);

}
