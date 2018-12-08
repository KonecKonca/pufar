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

public class NextMessageCommand extends AbstractCommand {

    private DialogService dialogService = new DialogServiceImpl();

    @Override
    public Router execute(RequestValue requestValue) {
        Router router = new Router();

        User currentUser = ((User)(requestValue.getAttribute(CommonConstant.CURRENT_USER)));
        long currentUserId = currentUser.getUserId();

        int msgStartNumber = (int)requestValue.getAttribute(CommonConstant.LAST_MESSAGE);

        List<User> users = Users.createUserArrayList(dialogService.searchPopularUser(currentUserId, CommonConstant.HOW_MUCH_USERS));

        List<UserMessage> lastMessagesWithTopUser = null;
        if(users.size() > 0){
            User currentOpponent = (User) requestValue.getAttribute(CommonConstant.CURRENT_OPPONENT);

            int fullSize = dialogService.numberOfMessagesBetween(currentUserId,currentOpponent.getUserId());
            int howMuchMessages = (int) requestValue.getAttribute(CommonConstant.HOW_MUCH_MESSAGES);

            msgStartNumber = msgStartNumber + howMuchMessages;
            if(msgStartNumber > fullSize){
                msgStartNumber = fullSize;
            }

            lastMessagesWithTopUser = dialogService.searchMessagesBetweenWithLimit(currentUserId, currentOpponent.getUserId(), msgStartNumber - howMuchMessages, howMuchMessages);
            requestValue.servletSessionPut(CommonConstant.LAST_MESSAGE, msgStartNumber);

        }

        requestValue.requestAttributePut(CommonConstant.TOP_USERS, users);
        requestValue.requestAttributePut(CommonConstant.LAST_MESSAGES, lastMessagesWithTopUser);
        router.setPagePath(PagePath.CHAT_PAGE.getJspPath());

        return router;
    }

}


