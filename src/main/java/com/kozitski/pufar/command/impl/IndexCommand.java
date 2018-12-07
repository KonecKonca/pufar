package com.kozitski.pufar.command.impl;

import com.kozitski.pufar.command.AbstractCommand;
import com.kozitski.pufar.command.PagePath;
import com.kozitski.pufar.command.RequestValue;
import com.kozitski.pufar.command.Router;

public class IndexCommand extends AbstractCommand {

    @Override
    public Router execute(RequestValue request) {

        Router router = new Router();
        router.setPagePath(PagePath.INDEX_PAGE.getJspPath());

        return router;
    }

}
