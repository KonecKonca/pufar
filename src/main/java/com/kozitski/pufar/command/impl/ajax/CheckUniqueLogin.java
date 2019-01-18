package com.kozitski.pufar.command.impl.ajax;

import com.kozitski.pufar.command.PagePath;
import com.kozitski.pufar.command.Router;
import com.kozitski.pufar.command.impl.dialog.SendMessageCommand;
import com.kozitski.pufar.command.response.ResponseCommand;
import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.exception.PufarValidationException;
import com.kozitski.pufar.service.user.UserService;
import com.kozitski.pufar.service.user.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * The type Check unique login.
 * Search into database received login and
 * in true case show user message
 */
public class CheckUniqueLogin implements ResponseCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(SendMessageCommand.class);

    private static final String CHECKED_LOGIN = "login";

    private static final String LOGIN_EXIST = "   x";

    private UserService userService = new UserServiceImpl();

    /**
     * Execute.
     *
     * @param request the request
     * @param response the response
     * @return the router
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Router router = new Router();
        router.setRouteType(Router.RouteType.RESPONSE_WRITE);
        router.setPagePath(PagePath.PROFILE_PAGE.getJspPath());

        String receivedLogin = request.getParameter(CHECKED_LOGIN);
        String resultMessage = "";
        try {
            Optional<User> optionalUser = userService.searchUserByLogin(receivedLogin);

            if (optionalUser.isPresent()) {
                resultMessage = LOGIN_EXIST;

            }
        } catch (PufarValidationException e) {
            LOGGER.warn("Input message incorrect diu to validation parameters", e);
        }

        response.getWriter().write(resultMessage);

        return router;

    }

}
