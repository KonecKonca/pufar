package com.kozitski.pufar.command.impl.notification;

import com.kozitski.pufar.command.request.AbstractCommand;
import com.kozitski.pufar.command.PagePath;
import com.kozitski.pufar.command.RequestValue;
import com.kozitski.pufar.command.Router;
import com.kozitski.pufar.util.CommonConstant;

// TODO: Auto-generated Javadoc
/**
 * The Class ChooseCreateNotificationCommand.
 */
public class ChooseCreateNotificationCommand extends AbstractCommand {

    /**
     * Execute.
     *
     * @param request the request
     * @return the router
     */
    @Override
    public Router execute(RequestValue request) {
        Router router = new Router();
        router.setPagePath(PagePath.CREATE_NOTIFICATION.getJspPath());

        request.servletSessionPut(CommonConstant.CURRENT_NOTIFICATION_IMAGE_PATH, null);

        return router;
    }

}
