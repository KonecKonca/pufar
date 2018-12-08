package com.kozitski.pufar.command.impl.dialog;

import com.kozitski.pufar.command.AbstractCommand;
import com.kozitski.pufar.command.PagePath;
import com.kozitski.pufar.command.RequestValue;
import com.kozitski.pufar.command.Router;
import com.kozitski.pufar.entity.message.UserMessage;
import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.entity.user.Users;
import com.kozitski.pufar.service.dialoge.DialogService;
import com.kozitski.pufar.service.dialoge.DialogServiceImpl;
import com.kozitski.pufar.util.CommonConstant;

import java.util.List;

public class GetContactCommand extends AbstractCommand {
    private DialogService dialogService = new DialogServiceImpl();

    @Override
    public Router execute(RequestValue requestValue) {
        Router router = new Router();

        User currentUser = ((User)(requestValue.getAttribute(CommonConstant.CURRENT_USER)));
        long currentUserId = currentUser.getUserId();

        int msgStartNumber;
        int howMuchMessages = (int) requestValue.getAttribute(CommonConstant.HOW_MUCH_MESSAGES);

        List<User> users = Users.createUserArrayList(dialogService.searchPopularUser(currentUserId, CommonConstant.HOW_MUCH_USERS));

        List<UserMessage> lastMessagesWithTopUser = null;
        if(users.size() > 0){
            User currentOpponent = users.get(0);
            requestValue.servletSessionPut(CommonConstant.CURRENT_OPPONENT, currentOpponent);

            msgStartNumber = dialogService.numberOfMessagesBetween(currentUserId, currentOpponent.getUserId());
            requestValue.servletSessionPut(CommonConstant.LAST_MESSAGE, msgStartNumber);

            if(msgStartNumber - howMuchMessages < 0){
                lastMessagesWithTopUser = dialogService.searchMessagesBetweenWithLimit(currentUserId, currentOpponent.getUserId(), 0, howMuchMessages);
            }
            else {
                lastMessagesWithTopUser = dialogService.searchMessagesBetweenWithLimit(currentUserId, currentOpponent.getUserId(), msgStartNumber - howMuchMessages, howMuchMessages);
            }
        }

        requestValue.requestAttributePut(CommonConstant.TOP_USERS, users);
        requestValue.requestAttributePut(CommonConstant.LAST_MESSAGES, lastMessagesWithTopUser);
        router.setPagePath(PagePath.CHAT_PAGE.getJspPath());

        return router;
    }

}
