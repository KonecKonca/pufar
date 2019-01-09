package com.kozitski.pufar.command.response;

import com.kozitski.pufar.command.Router;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ResponseCommand {

    Router execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;

}
