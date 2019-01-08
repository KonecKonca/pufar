package com.kozitski.pufar.controller;

import com.kozitski.pufar.command.PagePath;
import com.kozitski.pufar.util.CommonConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/invalidate")
public class LogoutController extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogoutController.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
            LOGGER.info("Session was invalidated");
        }

        request.getSession().setAttribute(CommonConstant.CURRENT_PAGE, PagePath.LOGIN_PAGE.getJspPath());

        response.sendRedirect(PagePath.LOGIN_PAGE.getJspPath());
    }

}
