package com.kozitski.pufar.command.impl.ajax;

import com.kozitski.pufar.command.PagePath;
import com.kozitski.pufar.command.Router;
import com.kozitski.pufar.command.response.ResponseCommand;
import com.kozitski.pufar.util.CommonConstant;
import com.kozitski.pufar.util.language.PufarLanguage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The Class CheckPasswordCommand.
 * Command to asynchronously compare entered passwords.
 * This command developed with plans in future to
 * not only compare passwords and check passwords on
 * conformity with rules same as only english symbols etc
 */
public class CheckPasswordCommand implements ResponseCommand {
    
    /** The Constant PASSWORD1. */
    private static final String PASSWORD1 = "password1";
    
    /** The Constant PASSWORD2. */
    private static final String PASSWORD2 = "password2";

    /** The Constant EQUALS_IDENTIFIER. */
    private static final String EQUALS_IDENTIFIER = "profilePasswordAreEquals";
    
    /** The Constant NOT_EQUALS_IDENTIFIER. */
    private static final String NOT_EQUALS_IDENTIFIER = "profilePasswordAreNotEquals";

    /**
     * Execute.
     *
     * @param request the request
     * @param response the response
     * @return the router
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws ServletException the servlet exception
     */
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Router router = new Router();
        router.setRouteType(Router.RouteType.RESPONSE_WRITE);
        router.setPagePath(PagePath.PROFILE_PAGE.getJspPath());

        String password1 = request.getParameter(PASSWORD1);
        String password2 = request.getParameter(PASSWORD2);

        PufarLanguage pufarLanguage = (PufarLanguage) request.getSession().getAttribute(CommonConstant.LOCALE);
        String message;

        if (password1 != null && password2 != null && !password1.isEmpty() && !password2.isEmpty() && password1.equals(password2)) {
            message = pufarLanguage.getValue(EQUALS_IDENTIFIER);
        } else {
            message = pufarLanguage.getValue(NOT_EQUALS_IDENTIFIER);
        }

        response.getWriter().write(message);

        return router;
    }

}
