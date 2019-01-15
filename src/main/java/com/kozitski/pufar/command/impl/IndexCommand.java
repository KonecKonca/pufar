package com.kozitski.pufar.command.impl;

import com.kozitski.pufar.command.request.AbstractCommand;
import com.kozitski.pufar.command.PagePath;
import com.kozitski.pufar.command.RequestValue;
import com.kozitski.pufar.command.Router;

/**
 * The Class IndexCommand.
 * Command for changing actual page on index page
 */
public class IndexCommand extends AbstractCommand {

    /**
     * Execute.
     *
     * @param request the request
     * @return the router
     */
    @Override
    public Router execute(RequestValue request) {

        Router router = new Router();
        router.setPagePath(PagePath.INDEX_PAGE.getJspPath());

        return router;
    }

}
