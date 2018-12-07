package com.kozitski.pufar.listener;

import com.kozitski.pufar.util.path.WebPathReturner;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class PufarContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        WebPathReturner.setWebPath(servletContextEvent.getServletContext().getRealPath("/"));

    }


}
