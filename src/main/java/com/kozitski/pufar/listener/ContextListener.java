package com.kozitski.pufar.listener;

import com.kozitski.pufar.database.ConnectionPool;
import com.kozitski.pufar.util.path.WebPathReturner;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        WebPathReturner.setWebPath(servletContextEvent.getServletContext().getRealPath("/"));
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ConnectionPool.getInstance().destroy();
    }


}
