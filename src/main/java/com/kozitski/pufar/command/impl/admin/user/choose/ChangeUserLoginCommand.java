package com.kozitski.pufar.command.impl.admin.user.choose;

import com.kozitski.pufar.command.*;
import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.service.user.UserService;
import com.kozitski.pufar.util.CommonConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

public class ChangeUserLoginCommand extends AbstractCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChangeUserLoginCommand.class);
    private static final String USER_ID = "id";
    private static final String LOGIN = "login";

    private static final String OK_INPUT_MESSAGE = "user's login was changed";
    private static final String BAD_INPUT_MESSAGE = "was entered incorrect user ID or new login exactly exist";

    @InjectService
    private UserService userService;

    @Override
    public Router execute(RequestValue request) {
        Router router = new Router();
        router.setRouteType(Router.RouteType.REDIRECT);
        router.setPagePath(PagePath.ADMIN_CONTROL_PANEL.getJspPath());

        try {
            long id = Long.parseLong(request.getAttribute(USER_ID).toString());
            User currentUser = (User) request.getAttribute(CommonConstant.CURRENT_USER);

            String login = request.getAttribute(LOGIN).toString();
            String newUtf8Login = new String(login.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

            if (userService.changeUserLogin(id, newUtf8Login, currentUser)) {
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
