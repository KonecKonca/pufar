package com.kozitski.pufar.dao.notification;

import com.kozitski.pufar.entity.notification.Notification;

import java.util.ArrayList;

public interface NotificationDao {

    ArrayList<Notification> searchTopNotificationsWithLimit(int limit);

}
