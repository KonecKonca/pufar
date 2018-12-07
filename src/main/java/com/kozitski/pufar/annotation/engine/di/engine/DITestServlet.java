package com.kozitski.pufar.annotation.engine.di.engine;

import com.kozitski.pufar.annotation.engine.di.engine.annotation.Inject;
import com.kozitski.pufar.annotation.engine.di.engine.annotation.InjectionServlet;
import com.kozitski.pufar.annotation.engine.di.engine.entity.DaoImpl;
import com.kozitski.pufar.annotation.engine.di.engine.entity.ServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/di")
public class DITestServlet extends InjectionServlet {

    @Inject(beanName = "dao")
    private DaoImpl daoImpl;

    @Inject(beanName = "service")
    private ServiceImpl serviceImpl;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().write("It is dao activity<br>");
        response.getWriter().write("\t" + daoImpl.toString());

        response.getWriter().write("It is sevice activity<br>");
        response.getWriter().write("\t" + serviceImpl.toString());
    }

}
