package com.kozitski.pufar.command.impl.autorization;

import com.kozitski.pufar.command.PagePath;
import com.kozitski.pufar.command.Router;
import com.kozitski.pufar.command.response.ResponseCommand;
import com.kozitski.pufar.util.CommonConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutCommand implements ResponseCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogoutCommand.class);

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        Router router = new Router();
        router.setRouteType(Router.RouteType.REDIRECT);
        router.setPagePath(PagePath.LOGIN_PAGE.getJspPath());


        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
            LOGGER.info("Session was invalidated");
        }

        request.getSession().setAttribute(CommonConstant.CURRENT_PAGE, PagePath.LOGIN_PAGE.getJspPath());

        return router;
    }


}
