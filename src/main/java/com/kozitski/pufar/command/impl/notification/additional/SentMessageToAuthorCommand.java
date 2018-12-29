package com.kozitski.pufar.command.impl.notification.additional;

import com.kozitski.pufar.command.*;
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
import java.util.*;

public class SentMessageToAuthorCommand extends AbstractCommand {

    private static final String OWNER = "chosenUser";

    @InjectService
    private UserService userService;
    @InjectService
    private DialogService dialogService;

    @Override
    public Router execute(RequestValue requestValue) {
        Router router = new Router();
        router.setPagePath(PagePath.CHAT_PAGE.getJspPath());

        String stringCurrentOpponentId = (String) requestValue.getAttribute(OWNER);
        long currentOpponentId = Long.parseLong(stringCurrentOpponentId);

        Optional<User> optionalUser = userService.searchUserById(currentOpponentId);
        if(optionalUser.isPresent()){
            List topUsers = (List) requestValue.getAttribute(CommonConstant.TOP_USERS);
            if(topUsers != null){
                topUsers.add(0, optionalUser.get());
            }
            else {
                dialogService.showDialogs(requestValue);

                List topUsersElse = (List) requestValue.getAttribute(CommonConstant.TOP_USERS);
                if(topUsersElse != null){
                    topUsersElse.add(0, optionalUser.get());
                    requestValue.servletSessionPut(CommonConstant.TOP_USERS, topUsersElse);
                }
                else {
                    requestValue.servletSessionPut(CommonConstant.TOP_USERS, new ArrayList<>(Collections.singletonList(optionalUser.get())));
                }
            }


            dialogService.chooseDialogWithUser(requestValue);
        }

        return router;
    }

}
