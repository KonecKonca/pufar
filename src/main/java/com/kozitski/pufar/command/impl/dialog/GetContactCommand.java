package com.kozitski.pufar.command.impl.dialog;

import com.kozitski.pufar.command.*;
import com.kozitski.pufar.command.request.AbstractCommand;
import com.kozitski.pufar.service.dialoge.DialogService;

/**
 * The Class GetContactCommand.
 * Command for loading chat page
 */
public class GetContactCommand extends AbstractCommand {

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
        dialogService.showDialogs(requestValue);
        router.setPagePath(PagePath.CHAT_PAGE.getJspPath());

        return router;
    }

}
