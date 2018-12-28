package com.kozitski.pufar.command.impl.autorization;

import com.kozitski.pufar.command.*;
import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.entity.user.Users;
import com.kozitski.pufar.exception.PufarServiceException;
import com.kozitski.pufar.service.user.UserService;
import com.kozitski.pufar.service.user.UserServiceImpl;
import com.kozitski.pufar.util.CommonConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegistrationCommand extends AbstractCommand {
    private static Logger LOGGER = LoggerFactory.getLogger(RegistrationCommand.class);
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";

    @InjectService
    private UserService service;

    @Override
    public Router execute(RequestValue request) {
        Router router = new Router();

        String currentLogin = request.getAttribute(LOGIN).toString();
        String currentPassword = request.getAttribute(PASSWORD).toString();

        try {
            User currentUser = service.addUser(currentLogin, currentPassword);
            router.setPagePath(PagePath.TEMPLATE_PAGE.getJspPath());
            request.servletSessionPut(CommonConstant.CURRENT_USER, currentUser);
        }
        catch (Exception e) {
            LOGGER.warn("user is not registered", e);
        }

        return router;
    }

}
