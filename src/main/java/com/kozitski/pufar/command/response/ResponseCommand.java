package com.kozitski.pufar.command.response;

import com.kozitski.pufar.command.Router;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// TODO: Auto-generated Javadoc
/**
 * The Interface ResponseCommand.
 */
public interface ResponseCommand {

    /**
     * Execute.
     *
     * @param request the request
     * @param response the response
     * @return the router
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws ServletException the servlet exception
     */
    Router execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;

}
