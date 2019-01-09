package com.kozitski.pufar.command.impl.profile;

import com.kozitski.pufar.command.*;
import com.kozitski.pufar.command.request.AbstractCommand;
import com.kozitski.pufar.entity.notification.Notification;
import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.service.notification.NotificationService;
import com.kozitski.pufar.util.CommonConstant;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class OpenProfilePageCommand.
 */
public class OpenProfilePageCommand extends AbstractCommand {

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
        router.setPagePath(PagePath.PROFILE_PAGE.getJspPath());


        User currentUser = (User) request.getAttribute(CommonConstant.CURRENT_USER);
        long currentUserId = currentUser.getUserId();

        List<Notification> currentUserNotifications = notificationService.searchAllNotificationsByAuthorId(currentUserId);
        request.servletSessionPut(CommonConstant.CURRENT_USER_NOTIFICATION, currentUserNotifications);

        return router;
    }

}
