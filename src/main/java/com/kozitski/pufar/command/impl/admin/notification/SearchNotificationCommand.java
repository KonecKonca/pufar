package com.kozitski.pufar.command.impl.admin.notification;

import com.kozitski.pufar.command.request.AbstractCommand;
import com.kozitski.pufar.command.PagePath;
import com.kozitski.pufar.command.RequestValue;
import com.kozitski.pufar.command.Router;

// TODO: Auto-generated Javadoc
/**
 * The Class SearchNotificationCommand.
 */
public class SearchNotificationCommand extends AbstractCommand {

    /**
     * Execute.
     *
     * @param request the request
     * @return the router
     */
    @Override
    public Router execute(RequestValue request) {
        Router router = new Router();
        router.setPagePath(PagePath.ADMIN_SEARCH_NOTIFICATION.getJspPath());
        return router;
    }

}
