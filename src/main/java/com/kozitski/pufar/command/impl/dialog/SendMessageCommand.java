package com.kozitski.pufar.command.impl.dialog;

import com.kozitski.pufar.command.*;
import com.kozitski.pufar.command.request.AbstractCommand;
import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.exception.PufarValidationException;
import com.kozitski.pufar.service.dialoge.DialogService;
import com.kozitski.pufar.util.CommonConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

// TODO: Auto-generated Javadoc
/**
 * The Class SendMessageCommand.
 */
public class SendMessageCommand extends AbstractCommand {
    
    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(SendMessageCommand.class);
    
    /** The Constant SENDING_MESSAGE. */
    private static final String SENDING_MESSAGE = "sentValue";

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
        router.setRouteType(Router.RouteType.REDIRECT);

        String sentMessage = requestValue.getAttribute(SENDING_MESSAGE).toString();

        User currentUser = (User) requestValue.getAttribute(CommonConstant.CURRENT_USER);
        User currentOpponent = (User) requestValue.getAttribute(CommonConstant.CURRENT_OPPONENT);

        try {
            dialogService.addMessage(currentUser.getUserId(), currentOpponent.getUserId(), new String(sentMessage.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
            dialogService.showDialogs(requestValue);
        } catch (PufarValidationException e) {
            LOGGER.warn("Input message incorrect diu to validation parameters", e);
        }

        return router;
    }


}
