package com.kozitski.pufar.controller;

import com.kozitski.pufar.command.PagePath;
import com.kozitski.pufar.command.RequestValue;
import com.kozitski.pufar.command.Router;
import com.kozitski.pufar.command.request.AbstractCommand;
import com.kozitski.pufar.command.request.CommandFactory;
import com.kozitski.pufar.command.response.CommandFactoryWithResponse;
import com.kozitski.pufar.command.response.CommandTypeWithResponse;
import com.kozitski.pufar.command.response.ResponseCommand;
import com.kozitski.pufar.service.number.NumberServiceImpl;
import com.kozitski.pufar.util.CommonConstant;
import com.kozitski.pufar.util.servlet.RequestValueTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The Class PufarCommonController.
 * Controller of applications which choose necessary
 * command and execute it
 */
@WebServlet("/pufar/*")
@MultipartConfig(fileSizeThreshold = 1024 * 1024
        , maxFileSize = 1024 * 1024 * 5
        , maxRequestSize = 1024 * 1024 * 5 * 5)
public class PufarCommonController extends HttpServlet {
    
    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(NumberServiceImpl.class);

    /** The Constant COMMAND_NAME. */
    private static final String COMMAND_NAME = "command";
    
    /** The Constant COMMAND_NAME_WITH_RESPONSE. */
    private static final String COMMAND_NAME_WITH_RESPONSE = "commandResponse";
    
    /** The Constant SUBSTRING_SLASH. */
    private static final int SUBSTRING_SLASH = 1;
    
    /** The Constant SLASH_LITERAL. */
    private static final char SLASH_LITERAL = '/';

    /**
     * Do get.
     *
     * @param request the request
     * @param response the response
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();
        if(path != null && !path.isEmpty() && !path.equals(String.valueOf(SLASH_LITERAL))) {
            String receivedString = path.substring(SUBSTRING_SLASH);
            request.setAttribute(CommonConstant.RECEIVED_STRING, receivedString);
            request.setAttribute(COMMAND_NAME_WITH_RESPONSE, CommandTypeWithResponse.SHOW_IMAGE.name());

            performQuery(request, response);
        }
        else { response.sendRedirect(PagePath.INDEX_PAGE.getJspPath()); }
    }

    /**
     * Do post.
     *
     * @param request the request
     * @param response the response
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        performQuery(request, response);
    }

    /**
     * Perform query.
     *
     * @param request the request
     * @param response the response
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void performQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Router router;

        String commandName = request.getParameter(COMMAND_NAME_WITH_RESPONSE);
        if(commandName == null){
            commandName = (String) request.getAttribute(COMMAND_NAME_WITH_RESPONSE);
        }
        ResponseCommand commandWithResponse = CommandFactoryWithResponse.chooseCommand(commandName);
        if (commandWithResponse != null) {
            router = commandWithResponse.execute(request, response);
        }
        else {
            AbstractCommand command = CommandFactory.chooseCommand(request.getParameter(COMMAND_NAME));
            RequestValue requestValue = RequestValueTransformer.transformFrom(request);

            router = command.execute(requestValue);
            RequestValueTransformer.transformTo(request, requestValue);
        }

        if (router.isForward()) {
            LOGGER.info("FORWARD on " + router.getPagePath());
            request.getSession().setAttribute(CommonConstant.CURRENT_PAGE, router.getPagePath());
            request.getRequestDispatcher(router.getPagePath()).forward(request, response);
        }
        else if (router.isRedirect()){
            LOGGER.info("REDIRECT on " + router.getPagePath());
            request.getSession().setAttribute(CommonConstant.CURRENT_PAGE, router.getPagePath());
            response.sendRedirect(router.getPagePath());
        }

    }

}
