package com.kozitski.pufar.command.impl.dialog;

import com.kozitski.pufar.command.*;
import com.kozitski.pufar.service.dialoge.DialogService;

public class ChangeOpponent extends AbstractCommand {

    @InjectService
    private DialogService dialogService;

    @Override
    public Router execute(RequestValue requestValue) {
        Router router = new Router();
        router.setPagePath(PagePath.CHAT_PAGE.getJspPath());
        router.setRouteType(Router.RouteType.REDIRECT);

        dialogService.updateTopUsersCash(requestValue);
        dialogService.chooseDialogWithUser(requestValue);

        return router;
    }


}
