package com.kozitski.pufar.command.impl.notification;

import com.kozitski.pufar.command.AbstractCommand;
import com.kozitski.pufar.command.PagePath;
import com.kozitski.pufar.command.RequestValue;
import com.kozitski.pufar.command.Router;

public class ChooseCreateNotificationCommand extends AbstractCommand {

    @Override
    public Router execute(RequestValue request) {
        Router router = new Router();
        router.setPagePath(PagePath.CREATE_NOTIFICATION.getJspPath());
        return router;
    }

}
