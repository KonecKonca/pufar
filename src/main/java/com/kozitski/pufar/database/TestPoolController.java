package com.kozitski.pufar.database;

import com.kozitski.pufar.dao.notification.NotificationDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@WebServlet("/pool")
public class TestPoolController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int counter = 1;

        NotificationDaoImpl notificationDao = new NotificationDaoImpl();
        for (int i = 0; i < 100; i++){
            notificationDao.searchTopNotificationsWithLimit(10);
            System.out.println("                " + counter++);
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


}
