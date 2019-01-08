package com.kozitski.pufar.command.impl.admin.notification.choose;

import com.kozitski.pufar.command.*;
import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.service.notification.NotificationService;
import com.kozitski.pufar.util.CommonConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

public class ChangeNotificationMessageCommand extends AbstractCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChangeNotificationMessageCommand.class);
    private static final String NOTIFICATION_ID = "id";
    private static final String COMMENT_MESSAGE = "message";

    private static final String OK_INPUT_MESSAGE = "message was successfully changed";
    private static final String BAD_INPUT_MESSAGE = "was entered incorrect notification id";


    @InjectService
    private NotificationService notificationService;

    @Override
    public Router execute(RequestValue request) {
        Router router = new Router();
        router.setRouteType(Router.RouteType.REDIRECT);
        router.setPagePath(PagePath.ADMIN_CONTROL_PANEL.getJspPath());

        try {
            long id = Long.parseLong(request.getAttribute(NOTIFICATION_ID).toString());
            String newMessage = request.getAttribute(COMMENT_MESSAGE).toString();

            String newUtf8Message = new String(newMessage.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

            User currentUser = (User) request.getAttribute(CommonConstant.CURRENT_USER);

            if (notificationService.changeNotificationMessage(id, newUtf8Message, currentUser)) {
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
