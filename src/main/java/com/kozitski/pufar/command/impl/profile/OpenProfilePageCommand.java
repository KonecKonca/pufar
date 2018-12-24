package com.kozitski.pufar.command.impl.profile;

import com.kozitski.pufar.command.AbstractCommand;
import com.kozitski.pufar.command.PagePath;
import com.kozitski.pufar.command.RequestValue;
import com.kozitski.pufar.command.Router;
import com.kozitski.pufar.service.notification.NotificationService;
import com.kozitski.pufar.service.notification.NotificationServiceImpl;

public class OpenProfilePageCommand extends AbstractCommand {
    private NotificationService notificationService = new NotificationServiceImpl();

    @Override
    public Router execute(RequestValue request) {
        Router router = new Router();
        router.setPagePath(PagePath.PROFILE_PAGE.getJspPath());



        return router;
    }

}
