package com.kozitski.pufar.command.impl.notification;

import com.kozitski.pufar.command.AbstractCommand;
import com.kozitski.pufar.command.PagePath;
import com.kozitski.pufar.command.RequestValue;
import com.kozitski.pufar.command.Router;
import com.kozitski.pufar.entity.notification.Notification;
import com.kozitski.pufar.entity.notification.UnitType;
import com.kozitski.pufar.service.notification.NotificationService;
import com.kozitski.pufar.service.notification.NotificationServiceImpl;
import com.kozitski.pufar.util.CommonConstant;

import java.util.ArrayList;

public class ShowNotificationByUnitTypeCommand extends AbstractCommand {
    private static final String UNIT_TYPE = "unitType";
    private static final int CHANGE_CURSOR_VALUE = 0;

    private NotificationService notificationService = new NotificationServiceImpl();

    @Override
    public Router execute(RequestValue request) {
        Router router = new Router();
        router.setPagePath(PagePath.TEMPLATE_PAGE.getJspPath());

        UnitType unitType = UnitType.valueOf((String) request.getAttribute(UNIT_TYPE));

        ArrayList<Notification> notifications = notificationService.searchNotificationsWithChangingCursor(request, unitType, CHANGE_CURSOR_VALUE);
        request.servletSessionPut(CommonConstant.CURRENT_NOTIFICATIONS, notifications);

        return  router;
    }

}
