package com.kozitski.pufar.command.impl.dialog;

import com.kozitski.pufar.command.*;
import com.kozitski.pufar.service.dialoge.DialogService;

public class GetContactCommand extends AbstractCommand {

    @InjectService
    private DialogService dialogService;

    @Override
    public Router execute(RequestValue requestValue) {
        Router router = new Router();
        dialogService.showDialogs(requestValue);
        router.setPagePath(PagePath.CHAT_PAGE.getJspPath());

        return router;
    }

}
