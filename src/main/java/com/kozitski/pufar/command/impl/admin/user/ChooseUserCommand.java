package com.kozitski.pufar.command.impl.admin.user;

import com.kozitski.pufar.command.request.AbstractCommand;
import com.kozitski.pufar.command.PagePath;
import com.kozitski.pufar.command.RequestValue;
import com.kozitski.pufar.command.Router;

/**
 * The Class ChooseUserCommand.
 * Command to change actual page on user search page
 */
public class ChooseUserCommand extends AbstractCommand {

    /**
     * Execute.
     *
     * @param request the request
     * @return the router
     */
    @Override
    public Router execute(RequestValue request) {
        Router router = new Router();
        router.setPagePath(PagePath.ADMIN_SEARCH_USER.getJspPath());
        return router;
    }

}
