package com.kozitski.pufar.command.impl.admin.user;

import com.kozitski.pufar.command.*;
import com.kozitski.pufar.command.request.AbstractCommand;
import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.service.user.UserService;
import com.kozitski.pufar.util.CommonConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class ChangeUserStatusCommand.
 * Command to change user status
 */
public class ChangeUserStatusCommand extends AbstractCommand {
    
    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ChangeUserStatusCommand.class);
    
    /** The Constant USER_ID. */
    private static final String USER_ID = "id";
    
    /** The Constant LOGIN. */
    private static final String LOGIN = "status";

    /** The Constant OK_INPUT_MESSAGE. */
    private static final String OK_INPUT_MESSAGE = "user's login was changed";
    
    /** The Constant BAD_INPUT_MESSAGE. */
    private static final String BAD_INPUT_MESSAGE = "was entered incorrect user ID or new login exactly exist";

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
        router.setRouteType(Router.RouteType.REDIRECT);
        router.setPagePath(PagePath.ADMIN_CONTROL_PANEL.getJspPath());

        try {
            long id = Long.parseLong(request.getAttribute(USER_ID).toString());
            User currentUser = (User) request.getAttribute(CommonConstant.CURRENT_USER);
            String status = request.getAttribute(LOGIN).toString();

            if (userService.changeUserStatusByUserId(id, status, currentUser)) {
                request.servletSessionPut(CommonConstant.ADMIN_INPUT_MESSAGE, OK_INPUT_MESSAGE);
            } else {
                request.servletSessionPut(CommonConstant.ADMIN_INPUT_MESSAGE, BAD_INPUT_MESSAGE);
            }
        } catch (NumberFormatException e) {
            LOGGER.warn(BAD_INPUT_MESSAGE, e);
            request.servletSessionPut(CommonConstant.ADMIN_INPUT_MESSAGE, BAD_INPUT_MESSAGE);
        }

        return router;
    }


}
