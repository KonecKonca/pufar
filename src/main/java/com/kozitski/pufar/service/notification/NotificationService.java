package com.kozitski.pufar.service.notification;

import com.kozitski.pufar.entity.notification.Notification;
import com.kozitski.pufar.entity.notification.NotificationParameters;

import java.util.ArrayList;

public interface NotificationService {

    ArrayList<Notification> searchTopNotificationsWithLimit(int limit);

    ArrayList<Notification> searchNotificationByParameters(NotificationParameters parameters);

}
