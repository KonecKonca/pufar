package com.kozitski.pufar.controller;

import com.kozitski.pufar.command.*;
import com.kozitski.pufar.command.impl.error.ErrorCommand;
import com.kozitski.pufar.util.request.RequestValueTransformer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/error")
public class ErrorController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        errorHandle(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        errorHandle(request, response);
    }

    private void errorHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AbstractCommand command = new ErrorCommand();
        RequestValue requestValue = RequestValueTransformer.transformFrom(request);

        Router router = command.execute(requestValue);
        RequestValueTransformer.transformTo(request, requestValue);

        if(router.isForward()){
            request.getRequestDispatcher(router.getPagePath()).forward(request, response);
        }
        else {
            response.sendRedirect(router.getPagePath());
        }
    }

}
