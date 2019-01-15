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
 * The Class ChangeNotificationCommand.
 */
public class ChangeNotificationCommand extends AbstractCommand {
    
    /** The Constant CHARACTER_IDENTIFIER. */
    private static final String CHARACTER_IDENTIFIER = "nextCharacter";

    /** The notification service. */
    @InjectService
    private NotificationService notificationService;

    /**
     * Execute.
     *
     * @param requestValue the request value
     * @return the router
     */
    @Override
    public Router execute(RequestValue requestValue) {
        Router router = new Router();
        router.setPagePath(PagePath.TEMPLATE_PAGE.getJspPath());

        List<Notification> notifications;
        UnitType unitType = (UnitType) requestValue.getAttribute(CommonConstant.NOTIFICATIONS_LAST_UNIT);

        boolean character = Boolean.parseBoolean((String) requestValue.getAttribute(CHARACTER_IDENTIFIER));
        if (character) {
            notifications = notificationService.searchNotificationsWithChangingCursor(requestValue, unitType, CommonConstant.HOW_MUCH_NOTIFICATIONS);
        } else {
            notifications = notificationService.searchNotificationsWithChangingCursor(requestValue, unitType, -CommonConstant.HOW_MUCH_NOTIFICATIONS);
        }
        requestValue.servletSessionPut(CommonConstant.CURRENT_NOTIFICATIONS, notifications);

        return router;
    }

}
