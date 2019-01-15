package com.kozitski.pufar.command.impl.admin.notification;

import com.kozitski.pufar.command.*;
import com.kozitski.pufar.command.request.AbstractCommand;
import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.service.notification.NotificationService;
import com.kozitski.pufar.util.CommonConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class DropNotificationCommand.
 * Command to drop notification by id
 */
public class DropNotificationCommand extends AbstractCommand {
    
    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(DropNotificationCommand.class);
    
    /** The Constant COMMENT_ID. */
    private static final String COMMENT_ID = "id";

    /** The Constant OK_INPUT_MESSAGE. */
    private static final String OK_INPUT_MESSAGE = "notification(+ binding comments, rates) was deleted ";
    
    /** The Constant BAD_INPUT_MESSAGE. */
    private static final String BAD_INPUT_MESSAGE = "was entered incorrect notification id";

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
        router.setRouteType(Router.RouteType.REDIRECT);
        router.setPagePath(PagePath.ADMIN_CONTROL_PANEL.getJspPath());

        try {
            long id = Long.parseLong(request.getAttribute(COMMENT_ID).toString());
            User currentUser = (User) request.getAttribute(CommonConstant.CURRENT_USER);

            if (notificationService.dropNotificationById(id, currentUser)) {
                request.servletSessionPut(CommonConstant.ADMIN_INPUT_MESSAGE, OK_INPUT_MESSAGE);
            } else {
                request.servletSessionPut(CommonConstant.ADMIN_INPUT_MESSAGE, BAD_INPUT_MESSAGE);
            }
        } catch (NumberFormatException e) {
            LOGGER.warn(BAD_INPUT_MESSAGE, e);
            request.servletSessionPut(CommonConstant.ADMIN_INPUT_MESSAGE, BAD_INPUT_MESSAGE);
        }

        return router;
    }

}
