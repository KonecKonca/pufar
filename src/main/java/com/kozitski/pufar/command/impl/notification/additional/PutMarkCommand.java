package com.kozitski.pufar.command.impl.notification.additional;

import com.kozitski.pufar.command.*;
import com.kozitski.pufar.controller.LogoutController;
import com.kozitski.pufar.entity.notification.UnitType;
import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.exception.PufarValidationException;
import com.kozitski.pufar.service.notification.NotificationService;
import com.kozitski.pufar.service.notification.NotificationServiceImpl;
import com.kozitski.pufar.util.CommonConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PutMarkCommand extends AbstractCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(PutMarkCommand.class);

    private static final String NOTIFICATION_ID = "notificationId";
    private static final String MARK = "radios";

    @InjectService
    private NotificationService notificationService;

    @Override
    public Router execute(RequestValue requestValue) {
        Router router = new Router();
        router.setPagePath(PagePath.NOTIFICATION_ADDITIONAL.getJspPath());

        String markString = (String) requestValue.getAttribute(MARK);
        if(markString != null){
            int mark = Integer.parseInt(markString);

            String notificationIdString = (String) requestValue.getAttribute(NOTIFICATION_ID);
            long notificationId = Long.parseLong(notificationIdString);

            User currentUser = (User) requestValue.getAttribute(CommonConstant.CURRENT_USER);
            long senderId = currentUser.getUserId();

            try {
                notificationService.putMark(requestValue, mark, senderId, notificationId);
            }
            catch (PufarValidationException e) {
                LOGGER.warn("incorrect input data", e);
            }
        }

        return router;
    }

}
