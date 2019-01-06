package com.kozitski.pufar.controller;

import com.kozitski.pufar.command.*;
import com.kozitski.pufar.service.number.NumberServiceImpl;
import com.kozitski.pufar.util.CommonConstant;
import com.kozitski.pufar.util.servlet.RequestValueTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/pufar")
public class PufarCommonController extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(NumberServiceImpl.class);

    private static final String COMMAND_NAME = "command";
    private static final String UTF8_ENCODING = "UTF-8";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(PagePath.INDEX_PAGE.getJspPath());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding(UTF8_ENCODING);

        AbstractCommand command = CommandFactory.chooseCommand(request.getParameter(COMMAND_NAME));
        RequestValue requestValue = RequestValueTransformer.transformFrom(request);

        Router router = command.execute(requestValue);

        RequestValueTransformer.transformTo(request, requestValue);

        request.getSession().setAttribute(CommonConstant.CURRENT_PAGE, router.getPagePath());

        if (router.isForward()) {
            LOGGER.info("FORWARD on " + router.getPagePath());
            request.getRequestDispatcher(router.getPagePath()).forward(request, response);
        } else {
            LOGGER.info("REDIRECT on " + router.getPagePath());
            response.sendRedirect(router.getPagePath());
        }

    }

}
