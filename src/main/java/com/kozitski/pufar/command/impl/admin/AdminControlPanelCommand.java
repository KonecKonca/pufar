package com.kozitski.pufar.command.impl.admin;

import com.kozitski.pufar.command.AbstractCommand;
import com.kozitski.pufar.command.PagePath;
import com.kozitski.pufar.command.RequestValue;
import com.kozitski.pufar.command.Router;

public class AdminControlPanelCommand extends AbstractCommand {

    @Override
    public Router execute(RequestValue request) {
        Router router = new Router();
        router.setPagePath(PagePath.ADMIN_CONTROL_PANEL.getJspPath());
        return router;
    }


}
