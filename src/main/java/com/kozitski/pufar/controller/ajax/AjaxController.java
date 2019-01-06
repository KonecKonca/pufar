package com.kozitski.pufar.controller.ajax;

import com.kozitski.pufar.util.CommonConstant;
import com.kozitski.pufar.util.language.PufarLanguage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet("/AjaxController")
public class AjaxController extends HttpServlet {
    private static final String CONTENT_TYPE = "text/plain";
    private static final String PASSWORD1 = "password1";
    private static final String PASSWORD2 = "password2";

    private static final String EQUALS_IDENTIFIER = "profilePasswordAreEquals";
    private static final String NOT_EQUALS_IDENTIFIER = "profilePasswordAreNotEquals";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);

        String password1 = request.getParameter(PASSWORD1);
        String password2 = request.getParameter(PASSWORD2);

        PufarLanguage pufarLanguage = (PufarLanguage) request.getSession().getAttribute(CommonConstant.LOCALE);
        String message;

        if (password1 != null && password2 != null && !password1.isEmpty() && !password2.isEmpty() && password1.equals(password2)){
            message = pufarLanguage.getValue(EQUALS_IDENTIFIER);
        } else {
            message = pufarLanguage.getValue(NOT_EQUALS_IDENTIFIER);
        }

        response.getWriter().write(message);

    }

}
