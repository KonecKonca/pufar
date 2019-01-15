package com.kozitski.pufar.command.impl.notification.additional;

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
 * The Class SentMessageToAuthorCommand.
 */
public class SentMessageToAuthorCommand extends AbstractCommand {
    
    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(SentMessageToAuthorCommand.class);

    /** The Constant OWNER. */
    private static final String OWNER = "chosenUser";
    
    /** The Constant MESSAGE_VALUE. */
    private static final String MESSAGE_VALUE = "messageValue";

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

        String stringCurrentOpponentId = (String) requestValue.getAttribute(OWNER);
        long currentOpponentId = Long.parseLong(stringCurrentOpponentId);

        User currentUser = (User) requestValue.getAttribute(CommonConstant.CURRENT_USER);
        long currentUserId = currentUser.getUserId();

        String messageValue = (String) requestValue.getAttribute(MESSAGE_VALUE);
        String utf8Message = new String(messageValue.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

        try {
            dialogService.addMessage(currentUserId, currentOpponentId, utf8Message);
            requestValue.servletSessionPut(CommonConstant.CURRENT_OPPONENT, null);
            dialogService.showDialogs(requestValue);
        }
        catch (PufarValidationException e) {
            router.setPagePath(PagePath.NOTIFICATION_ADDITIONAL.getJspPath());
            LOGGER.warn("message was not send", e);
        }

        return router;
    }



}
