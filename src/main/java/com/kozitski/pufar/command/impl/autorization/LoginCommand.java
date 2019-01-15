package com.kozitski.pufar.command.impl.autorization;

import com.kozitski.pufar.command.*;
import com.kozitski.pufar.command.request.AbstractCommand;
import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.exception.PufarValidationException;
import com.kozitski.pufar.service.autorization.LoginService;
import com.kozitski.pufar.util.CommonConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

// TODO: Auto-generated Javadoc
/**
 * The Class LoginCommand.
 */
public class LoginCommand extends AbstractCommand {
    
    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginCommand.class);
    
    /** The Constant LOGIN. */
    private static final String LOGIN = "login";
    
    /** The Constant PASSWORD. */
    private static final String PASSWORD = "password";

    /** The login service. */
    @InjectService
    private LoginService loginService;

    /**
     * Execute.
     *
     * @param request the request
     * @return the router
     */
    @Override
    public Router execute(RequestValue request) {

        Router router = new Router();
        router.setPagePath(PagePath.TEMPLATE_PAGE.getJspPath());
        router.setRouteType(Router.RouteType.REDIRECT);

        String login = request.getAttribute(LOGIN).toString();
        String password = request.getAttribute(PASSWORD).toString();

        String utf8Login = new String(login.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        String utf8Password = new String(password.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

        try{
            Optional<User> user = loginService.searchUserByLoginPassword(utf8Login, utf8Password);

            if(user.isPresent() && !user.get().isBanned()){
                request.servletSessionPut(CommonConstant.CURRENT_USER, user.get());
                router.setPagePath(PagePath.TEMPLATE_PAGE.getJspPath());
            }
            else {
                LOGGER.warn("User is not authorized");
                router.setPagePath(PagePath.LOGIN_PAGE.getJspPath());
            }
        }
        catch (PufarValidationException e){
            LOGGER.warn("Were entered incorrect data", e);
            router.setPagePath(PagePath.LOGIN_PAGE.getJspPath());
        }

        return router;
    }

}
