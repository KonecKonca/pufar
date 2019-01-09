package com.kozitski.pufar.command.impl.notification;

import com.kozitski.pufar.command.*;
import com.kozitski.pufar.command.request.AbstractCommand;
import com.kozitski.pufar.entity.notification.Notification;
import com.kozitski.pufar.entity.notification.UnitType;
import com.kozitski.pufar.service.notification.NotificationService;
import com.kozitski.pufar.util.CommonConstant;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class ShowNotificationByUnitTypeCommand.
 */
public class ShowNotificationByUnitTypeCommand extends AbstractCommand {
    
    /** The Constant UNIT_TYPE. */
    private static final String UNIT_TYPE = "unitType";
    
    /** The Constant CHANGE_CURSOR_VALUE. */
    private static final int CHANGE_CURSOR_VALUE = 0;

    /** The notification service. */
    @InjectService
    private NotificationService notificationService;

    /**
     * Execute.
     *
     * @param request the request
     * @return the router
     */
    @Override
    public Router execute(RequestValue request) {
        Router router = new Router();
        router.setPagePath(PagePath.TEMPLATE_PAGE.getJspPath());

        UnitType unitType = UnitType.valueOf((String) request.getAttribute(UNIT_TYPE));

        List<Notification> notifications = notificationService.searchNotificationsWithChangingCursor(request, unitType, CHANGE_CURSOR_VALUE);
        request.servletSessionPut(CommonConstant.CURRENT_NOTIFICATIONS, notifications);

        return router;
    }

}
