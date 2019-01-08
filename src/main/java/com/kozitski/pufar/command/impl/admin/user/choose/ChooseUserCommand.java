package com.kozitski.pufar.command.impl.admin.user.choose;

import com.kozitski.pufar.command.request.AbstractCommand;
import com.kozitski.pufar.command.PagePath;
import com.kozitski.pufar.command.RequestValue;
import com.kozitski.pufar.command.Router;

public class ChooseUserCommand extends AbstractCommand {

    @Override
    public Router execute(RequestValue request) {
        Router router = new Router();
        router.setPagePath(PagePath.ADMIN_SEARCH_USER.getJspPath());
        return router;
    }

}
