package com.kozitski.pufar.dao.notification;

import com.kozitski.pufar.entity.notification.Notification;
import com.kozitski.pufar.entity.notification.NotificationParameters;
import com.kozitski.pufar.entity.notification.UnitType;

import java.util.ArrayList;

public interface NotificationDao {

    ArrayList<Notification> searchTopNotificationsWithLimit(int limit);

    ArrayList<Notification> searchNotificationByParameters(NotificationParameters parameters);

}
