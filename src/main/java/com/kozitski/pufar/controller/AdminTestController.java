package com.kozitski.pufar.controller;

import com.kozitski.pufar.command.PagePath;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


// remake with custom tag (additional button on template page for admins)
@WebServlet("/admin")
public class AdminTestController  extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(PagePath.ADMIN_CONTROL_PANEL.getJspPath()).forward(req, resp);
    }

}
