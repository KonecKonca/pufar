package com.kozitski.pufar.command.impl.dialog;

import com.kozitski.pufar.command.AbstractCommand;
import com.kozitski.pufar.command.PagePath;
import com.kozitski.pufar.command.RequestValue;
import com.kozitski.pufar.command.Router;
import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.service.dialoge.DialogService;
import com.kozitski.pufar.service.dialoge.DialogServiceImpl;
import com.kozitski.pufar.util.CommonConstant;
import com.kozitski.pufar.validation.validator.CommentValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

public class SendMessageCommand extends AbstractCommand {
    private static Logger LOGGER = LoggerFactory.getLogger(SendMessageCommand.class);
    private static final String SENDING_MESSAGE = "sentValue";

    private DialogService dialogService = new DialogServiceImpl();

    @Override
    public Router execute(RequestValue requestValue) {
        Router router = new Router();
        router.setPagePath(PagePath.CHAT_PAGE.getJspPath());

        String sentMessage = requestValue.getAttribute(SENDING_MESSAGE).toString();

        User currentUser = (User) requestValue.getAttribute(CommonConstant.CURRENT_USER);
        User currentOpponent = (User) requestValue.getAttribute(CommonConstant.CURRENT_OPPONENT);

        try {
            dialogService.addMessage(currentUser.getUserId(), currentOpponent.getUserId(), new String(sentMessage.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
        }
        catch (Exception e){
            LOGGER.warn("Input message incorrect diu to validation parameters", e);
        }
        dialogService.showDialogs(requestValue);

        return router;
    }


}
