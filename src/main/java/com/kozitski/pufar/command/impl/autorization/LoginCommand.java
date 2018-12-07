package com.kozitski.pufar.command.impl.autorization;

import com.kozitski.pufar.command.AbstractCommand;
import com.kozitski.pufar.command.PagePath;
import com.kozitski.pufar.command.RequestValue;
import com.kozitski.pufar.command.Router;
import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.service.autorization.LoginService;
import com.kozitski.pufar.service.autorization.LoginServiceImpl;
import com.kozitski.pufar.util.CommonConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;


import static com.kozitski.pufar.util.CommonConstant.CURRENT_USER;

public class LoginCommand extends AbstractCommand {
    private static Logger LOGGER = LoggerFactory.getLogger(LoginCommand.class);
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";

    private LoginService loginService = new LoginServiceImpl();

    @Override
    public Router execute(RequestValue request){

        Router router = new Router();
        router.setPagePath(PagePath.TEMPLATE_PAGE.getJspPath());

        String login = request.getAttribute(LOGIN).toString();
        String password = request.getAttribute(PASSWORD).toString();

        try{
            Optional<User> user = loginService.searchUserByLoginPassword(login, password);

            if(user.isPresent()){
                request.servletSessionPut(CommonConstant.CURRENT_USER, user.get());
                router.setPagePath(PagePath.TEMPLATE_PAGE.getJspPath());
            }
            else {
                LOGGER.warn("User is not authorized");
                router.setPagePath(PagePath.LOGIN_PAGE.getJspPath());
            }
        }
        catch (Exception ex){
            router.setPagePath(PagePath.LOGIN_PAGE.getJspPath());
        }


        return router;
    }

}
