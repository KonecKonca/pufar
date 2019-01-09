package com.kozitski.pufar.command.impl.admin.user;

import com.kozitski.pufar.command.*;
import com.kozitski.pufar.command.request.AbstractCommand;
import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.entity.user.UserParameter;
import com.kozitski.pufar.entity.user.UserStatus;
import com.kozitski.pufar.service.user.UserService;
import com.kozitski.pufar.util.CommonConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class SearchUserCommand.
 */
public class SearchUserCommand extends AbstractCommand {
    
    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(SearchUserCommand.class);

    /** The Constant USER_ID. */
    private static final String USER_ID = "adminSearchUserId";
    
    /** The Constant USER_LOGIN_START. */
    private static final String USER_LOGIN_START = "adminSearchUserLoginStart";
    
    /** The Constant USER_STATUS. */
    private static final String USER_STATUS = "adminSearchUserStatus";

    /** The Constant OK_INPUT_MESSAGE. */
    private static final String OK_INPUT_MESSAGE = "were founded next users: ";
    
    /** The Constant BAD_INPUT_MESSAGE. */
    private static final String BAD_INPUT_MESSAGE = "Were entered incorrect user searching attributes";

    /** The user service. */
    @InjectService
    private UserService userService;

    /**
     * Execute.
     *
     * @param request the request
     * @return the router
     */
    @Override
    public Router execute(RequestValue request) {
        Router router = new Router();
        router.setPagePath(PagePath.ADMIN_CONTROL_PANEL.getJspPath());

        UserParameter parameters = new UserParameter();
        try {

            String stringId = (String) request.getAttribute(USER_ID);
            if (stringId != null && !stringId.isEmpty()) {
                long id = Long.parseLong(stringId);
                parameters.setUserId(id);
            }
            String stringLoginStart = (String) request.getAttribute(USER_LOGIN_START);
            if (stringLoginStart != null && !stringLoginStart.isEmpty()) {
                parameters.setLoginStart(stringLoginStart);
            }
            String stringStatus = (String) request.getAttribute(USER_STATUS);
            if (stringStatus != null && !stringStatus.isEmpty()) {
                parameters.setStatus(UserStatus.valueOf(stringStatus.toUpperCase()));
            }

            request.servletSessionPut(CommonConstant.ADMIN_INPUT_MESSAGE, OK_INPUT_MESSAGE);

            List<User> users = userService.searchUsersByParameters(parameters);
            request.servletSessionPut(CommonConstant.ADMIN_INPUT_RESULT, users);

        } catch (IllegalArgumentException | ClassCastException e) {
            LOGGER.warn(BAD_INPUT_MESSAGE, e);
            request.servletSessionPut(CommonConstant.ADMIN_INPUT_MESSAGE, BAD_INPUT_MESSAGE);
        }

        return router;
    }

}
