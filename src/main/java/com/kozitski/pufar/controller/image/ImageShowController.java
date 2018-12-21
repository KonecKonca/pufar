package com.kozitski.pufar.controller.image;

import com.kozitski.pufar.entity.notification.Notification;
import com.kozitski.pufar.util.CommonConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

@WebServlet("/imageShow/*")
public class ImageShowController extends HttpServlet {
    private static Logger LOGGER = LoggerFactory.getLogger(ImageShowController.class);
    private static final int SUBSTRING_SLASH = 1;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String path = request.getPathInfo();
        path = path.substring(SUBSTRING_SLASH);

        long notificationId =  Long.parseLong(path);
        @SuppressWarnings("unchecked")
        ArrayList<Notification> notifications = (ArrayList<Notification>) request.getSession().getAttribute(CommonConstant.CURRENT_NOTIFICATIONS);

        Notification currentNotification = null;
        for(Notification notification : notifications){
            if(notification.getNotificationId() == notificationId){
                currentNotification = notification;
            }
        }

        try {
            BufferedImage image = Objects.requireNonNull(currentNotification).getImage();
            response.setContentType("image/png");
            ImageIO.write(image, "png", response.getOutputStream());

        }
        catch (IOException | NullPointerException e) {
            LOGGER.warn("Notification image wasn't show");
        }

    }

}
