package com.kozitski.pufar.controller;

import com.kozitski.pufar.command.PagePath;
import com.kozitski.pufar.command.RequestValue;
import com.kozitski.pufar.command.Router;
import com.kozitski.pufar.command.request.AbstractCommand;
import com.kozitski.pufar.command.request.CommandFactory;
import com.kozitski.pufar.command.response.CommandFactoryWithResponse;
import com.kozitski.pufar.command.response.ResourceCommand;
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
    private static final String COMMAND_NAME_WITH_RESPONSE = "commandResponse";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(PagePath.INDEX_PAGE.getJspPath());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Router router;

        ResourceCommand commandWithResponse = CommandFactoryWithResponse.chooseCommand(request.getParameter(COMMAND_NAME_WITH_RESPONSE));
        if (commandWithResponse != null) {
            router = commandWithResponse.execute(request, response);
        } else {
            AbstractCommand command = CommandFactory.chooseCommand(request.getParameter(COMMAND_NAME));
            RequestValue requestValue = RequestValueTransformer.transformFrom(request);

            router = command.execute(requestValue);
            RequestValueTransformer.transformTo(request, requestValue);
        }

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
