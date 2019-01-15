package com.kozitski.pufar.command.impl.dialog;

import com.kozitski.pufar.command.*;
import com.kozitski.pufar.command.request.AbstractCommand;
import com.kozitski.pufar.service.dialoge.DialogService;

/**
 * The Class NextMessageCommand.
 * Command for going to next messages
 */
public class NextMessageCommand extends AbstractCommand {

    /** The dialog service. */
    @InjectService
    private DialogService dialogService;

    /**
     * Execute.
     *
     * @param requestValue the request value
     * @return the router
     */
    @Override
    public Router execute(RequestValue requestValue) {
        Router router = new Router();
        router.setPagePath(PagePath.CHAT_PAGE.getJspPath());
        dialogService.showNextDialogs(requestValue);

        return router;
    }

}


