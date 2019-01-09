package com.kozitski.pufar.command.impl.image;

import com.kozitski.pufar.command.Router;
import com.kozitski.pufar.command.response.ResponseCommand;
import com.kozitski.pufar.entity.notification.Notification;
import com.kozitski.pufar.util.CommonConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class ShowImageCommand implements ResponseCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShowImageCommand.class);

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response){
        Router router = new Router();
        router.setRouteType(Router.RouteType.RESPONSE_WRITE);

        String receivedString = (String) request.getAttribute(CommonConstant.RECEIVED_STRING);

        long notificationId = Long.parseLong(receivedString);
        @SuppressWarnings("unchecked")
        ArrayList<Notification> notifications = (ArrayList<Notification>) request.getSession().getAttribute(CommonConstant.CURRENT_NOTIFICATIONS);

        Notification currentNotification = null;
        for (Notification notification : notifications) {
            if (notification.getNotificationId() == notificationId) {
                currentNotification = notification;
            }
        }

        try {
            BufferedImage image = Objects.requireNonNull(currentNotification).getImage();
            response.setContentType("image/png");
            ImageIO.write(image, "png", response.getOutputStream());

        } catch (IOException | NullPointerException e) {
            LOGGER.warn("Notification image wasn't show");
        }

        return router;
    }


}
