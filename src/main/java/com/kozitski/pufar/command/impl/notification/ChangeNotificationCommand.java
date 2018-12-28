package com.kozitski.pufar.command.impl.notification;

import com.kozitski.pufar.command.*;
import com.kozitski.pufar.entity.notification.Notification;
import com.kozitski.pufar.entity.notification.UnitType;
import com.kozitski.pufar.service.notification.NotificationService;
import com.kozitski.pufar.service.notification.NotificationServiceImpl;
import com.kozitski.pufar.util.CommonConstant;

import java.util.ArrayList;

public class ChangeNotificationCommand extends AbstractCommand {
    private static final String CHARACTER_IDENTIFIER = "nextCharacter";

    @InjectService
    private NotificationService notificationService;

    @Override
    public Router execute(RequestValue requestValue) {
        Router router = new Router();
        router.setPagePath(PagePath.TEMPLATE_PAGE.getJspPath());

        ArrayList<Notification> notifications;
        UnitType unitType = (UnitType) requestValue.getAttribute(CommonConstant.NOTIFICATIONS_LAST_UNIT);

        boolean character = Boolean.parseBoolean((String) requestValue.getAttribute(CHARACTER_IDENTIFIER));
        if(character){
            notifications = notificationService.searchNotificationsWithChangingCursor(requestValue, unitType, CommonConstant.HOW_MUCH_NOTIFICATIONS);
        }
        else{
            notifications = notificationService.searchNotificationsWithChangingCursor(requestValue, unitType, -CommonConstant.HOW_MUCH_NOTIFICATIONS);
        }
        requestValue.servletSessionPut(CommonConstant.CURRENT_NOTIFICATIONS, notifications);

        return router;
    }

}
