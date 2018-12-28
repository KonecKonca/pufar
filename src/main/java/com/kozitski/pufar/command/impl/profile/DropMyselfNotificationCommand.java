package com.kozitski.pufar.command.impl.profile;

import com.kozitski.pufar.command.*;
import com.kozitski.pufar.entity.notification.Notification;
import com.kozitski.pufar.service.notification.NotificationService;
import com.kozitski.pufar.service.notification.NotificationServiceImpl;
import com.kozitski.pufar.util.CommonConstant;

import java.util.ArrayList;
import java.util.List;

public class DropMyselfNotificationCommand extends AbstractCommand {
    private static final String DROPPED_NOTIFICATION_ID = "notificationId";

    @InjectService
    private NotificationService notificationService;

    @Override
    @SuppressWarnings("unchecked")
    public Router execute(RequestValue request) {
        Router router = new Router();
        router.setPagePath(PagePath.PROFILE_PAGE.getJspPath());

        String stringNotificationId = (String) request.getAttribute(DROPPED_NOTIFICATION_ID);
        long notificationId = Long.parseLong(stringNotificationId);

        notificationService.dropMyselfNotificationById(notificationId);

        // drop notification in session
        ArrayList<Notification> notifications = (ArrayList<Notification>) request.getAttribute(CommonConstant.CURRENT_USER_NOTIFICATION);
        notifications.removeIf(n -> n.getNotificationId() == notificationId);

        return router;
    }


}
