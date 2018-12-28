package com.kozitski.pufar.command.impl.admin.notification.choose;

import com.kozitski.pufar.command.*;
import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.service.notification.NotificationService;
import com.kozitski.pufar.service.notification.NotificationServiceImpl;
import com.kozitski.pufar.util.CommonConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChangeNotificationMessageCommand extends AbstractCommand {
    private static Logger LOGGER = LoggerFactory.getLogger(ChangeNotificationMessageCommand.class);
    private static final String NOTIFICATION_ID = "id";
    private static final String COMMENT_MESSAGE = "message";

    private static final String OK_INPUT_MESSAGE = "message was successfully changed";
    private static final String BAD_INPUT_MESSAGE = "was entered incorrect notification id";


    @InjectService
    private NotificationService notificationService;

    @Override
    public Router execute(RequestValue request) {
        Router router = new Router();
        router.setPagePath(PagePath.ADMIN_CONTROL_PANEL.getJspPath());

        try {
            long id = Long.parseLong(request.getAttribute(NOTIFICATION_ID).toString());
            String newMessage = request.getAttribute(COMMENT_MESSAGE).toString();
            User currentUser = (User) request.getAttribute(CommonConstant.CURRENT_USER);

            if(notificationService.changeNotificationMessage(id, newMessage, currentUser)){
                request.requestAttributePut(CommonConstant.ADMIN_INPUT_MESSAGE, OK_INPUT_MESSAGE);
            }
            else {
                request.requestAttributePut(CommonConstant.ADMIN_INPUT_MESSAGE, BAD_INPUT_MESSAGE);
            }
        }
        catch (NumberFormatException e){
            LOGGER.warn(BAD_INPUT_MESSAGE, e);
            request.requestAttributePut(CommonConstant.ADMIN_INPUT_MESSAGE, BAD_INPUT_MESSAGE);
        }

        return router;
    }
}
