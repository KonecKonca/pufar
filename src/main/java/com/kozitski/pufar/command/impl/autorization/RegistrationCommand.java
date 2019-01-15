package com.kozitski.pufar.command.impl.autorization;

import com.kozitski.pufar.command.*;
import com.kozitski.pufar.command.request.AbstractCommand;
import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.exception.PufarServiceException;
import com.kozitski.pufar.exception.PufarValidationException;
import com.kozitski.pufar.service.user.UserService;
import com.kozitski.pufar.util.CommonConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * The Class RegistrationCommand.
 * Command to change actual page on registration page
 */
public class RegistrationCommand extends AbstractCommand {
    
    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationCommand.class);
    
    /** The Constant LOGIN. */
    private static final String LOGIN = "login";
    
    /** The Constant PASSWORD. */
    private static final String PASSWORD = "password";

    /** The service. */
    @InjectService
    private UserService service;

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

        String currentLogin = request.getAttribute(LOGIN).toString();
        String currentPassword = request.getAttribute(PASSWORD).toString();

        String utf8Login = new String(currentLogin.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        String utf8Password = new String(currentPassword.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

        try {
            User user = service.addUser(utf8Login, utf8Password);

            Optional<User> optionalUser = service.searchUserById(user.getUserId());
            if (optionalUser.isPresent()) {
                User currentUser = optionalUser.get();

                router.setPagePath(PagePath.TEMPLATE_PAGE.getJspPath());
                request.servletSessionPut(CommonConstant.CURRENT_USER, currentUser);
            }
        } catch (PufarServiceException e) {
            LOGGER.warn("user is not registered", e);
        } catch (PufarValidationException e) {
            LOGGER.warn("incorrect input data", e);
        }

        return router;
    }

}
