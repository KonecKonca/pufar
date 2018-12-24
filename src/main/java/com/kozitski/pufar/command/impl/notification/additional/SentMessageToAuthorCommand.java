package com.kozitski.pufar.command.impl.notification.additional;

import com.kozitski.pufar.command.AbstractCommand;
import com.kozitski.pufar.command.PagePath;
import com.kozitski.pufar.command.RequestValue;
import com.kozitski.pufar.command.Router;
import com.kozitski.pufar.controller.LogoutController;
import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.service.dialoge.DialogService;
import com.kozitski.pufar.service.dialoge.DialogServiceImpl;
import com.kozitski.pufar.service.user.UserService;
import com.kozitski.pufar.service.user.UserServiceImpl;
import com.kozitski.pufar.util.CommonConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Optional;

public class SentMessageToAuthorCommand extends AbstractCommand {
    private static Logger LOGGER = LoggerFactory.getLogger(SentMessageToAuthorCommand.class);

    private static final String RECEIVED_MESSAGE = "inputMessage";
    private static final String OWNER = "ownerId";

    private UserService userService = new UserServiceImpl();
    private DialogService dialogService = new DialogServiceImpl();

    @Override
    @SuppressWarnings("unchecked")
    public Router execute(RequestValue requestValue) {
        Router router = new Router();
        router.setPagePath(PagePath.CHAT_PAGE.getJspPath());

        String message = (String) requestValue.getAttribute(RECEIVED_MESSAGE);

        String stringOwnerId = (String) requestValue.getAttribute(OWNER);
        long ownerId = Long.parseLong(stringOwnerId);

        Optional<User> opponent = userService.searchUserById(ownerId);
        if(opponent.isPresent()){
            User currentUser = (User) requestValue.getAttribute(CommonConstant.CURRENT_USER);
            User currentOpponent = opponent.get();

            try {
                dialogService.addMessage(currentUser.getUserId(), currentOpponent.getUserId(), new String(message.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
            }
            catch (Exception e){
                LOGGER.warn("Input message incorrect diu to validation parameters", e);
            }

            ArrayList<User> users = (ArrayList<User>) requestValue.getAttribute(CommonConstant.TOP_USERS);
            users.add(0, currentOpponent);
            requestValue.servletSessionPut(CommonConstant.CURRENT_OPPONENT, currentOpponent);

            dialogService.showDialogs(requestValue);
        }

        return router;
    }

}
