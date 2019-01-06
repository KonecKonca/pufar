package com.kozitski.pufar.command.impl.dialog;

import com.kozitski.pufar.command.*;
import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.service.dialoge.DialogService;
import com.kozitski.pufar.util.CommonConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

public class SendMessageCommand extends AbstractCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(SendMessageCommand.class);
    private static final String SENDING_MESSAGE = "sentValue";

    @InjectService
    private DialogService dialogService;

    @Override
    public Router execute(RequestValue requestValue) {
        Router router = new Router();
        router.setPagePath(PagePath.CHAT_PAGE.getJspPath());

        String sentMessage = requestValue.getAttribute(SENDING_MESSAGE).toString();

        User currentUser = (User) requestValue.getAttribute(CommonConstant.CURRENT_USER);
        User currentOpponent = (User) requestValue.getAttribute(CommonConstant.CURRENT_OPPONENT);

        try {
            dialogService.addMessage(currentUser.getUserId(), currentOpponent.getUserId(), new String(sentMessage.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
        } catch (Exception e) {
            LOGGER.warn("Input message incorrect diu to validation parameters", e);
        }
        dialogService.showDialogs(requestValue);

        return router;
    }


}
