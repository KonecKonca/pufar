package com.kozitski.pufar.command.impl.admin.user.execute;

import com.kozitski.pufar.command.*;
import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.entity.user.UserParameter;
import com.kozitski.pufar.entity.user.UserStatus;
import com.kozitski.pufar.exception.PufarValidationException;
import com.kozitski.pufar.service.user.UserService;
import com.kozitski.pufar.service.user.UserServiceImpl;
import com.kozitski.pufar.util.CommonConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class SearchUserCommand extends AbstractCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(SearchUserCommand.class);

    private static final String USER_ID = "adminSearchUserId";
    private static final String USER_LOGIN_START = "adminSearchUserLoginStart";
    private static final String USER_STATUS = "adminSearchUserStatus";

    private static final String OK_INPUT_MESSAGE = "were founded next users: ";
    private static final String BAD_INPUT_MESSAGE = "Were entered incorrect user searching attributes";

    @InjectService
    private UserService userService;

    @Override
    public Router execute(RequestValue request) {
        Router router = new Router();
        router.setPagePath(PagePath.ADMIN_CONTROL_PANEL.getJspPath());

        UserParameter parameters = new UserParameter();
        try {

            String stringId = (String) request.getAttribute(USER_ID);
            if(stringId != null && !stringId.isEmpty()){
                long id = Long.parseLong(stringId);
                parameters.setUserId(id);
            }
            String stringLoginStart = (String) request.getAttribute(USER_LOGIN_START);
            if(stringLoginStart != null && !stringLoginStart.isEmpty()){
                parameters.setLoginStart(stringLoginStart);
            }
            String stringStatus = (String) request.getAttribute(USER_STATUS);
            if(stringStatus != null && !stringStatus.isEmpty()){
                parameters.setStatus(UserStatus.valueOf(stringStatus.toUpperCase()));
            }

            request.requestAttributePut(CommonConstant.ADMIN_INPUT_MESSAGE, OK_INPUT_MESSAGE);

            ArrayList<User> users = userService.searchUsersByParameters(parameters);
            request.requestAttributePut(CommonConstant.ADMIN_INPUT_RESULT, users);

        }
        catch (IllegalArgumentException | ClassCastException e){
            LOGGER.warn(BAD_INPUT_MESSAGE, e);
            request.requestAttributePut(CommonConstant.ADMIN_INPUT_MESSAGE, BAD_INPUT_MESSAGE);
        }

        return router;
    }


}
