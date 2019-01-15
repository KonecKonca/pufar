package com.kozitski.pufar.command.impl.notification;

import com.kozitski.pufar.command.*;
import com.kozitski.pufar.command.request.AbstractCommand;
import com.kozitski.pufar.entity.notification.Notification;
import com.kozitski.pufar.entity.notification.UnitType;
import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.exception.PufarServiceException;
import com.kozitski.pufar.exception.PufarValidationException;
import com.kozitski.pufar.service.notification.NotificationService;
import com.kozitski.pufar.util.CommonConstant;
import com.kozitski.pufar.util.language.PufarLanguage;
import com.kozitski.pufar.util.path.WebPathReturner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * The Class CreateNotificationCommand.
 * Command for creating notification
 */
public class CreateNotificationCommand extends AbstractCommand {
    
    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(CreateNotificationCommand.class);

    /** The Constant RADIO_BUTTON. */
    private static final String RADIO_BUTTON = "radios";

    /** The Constant NOTIFICATION_MESSAGE. */
    private static final String NOTIFICATION_MESSAGE = "createNotificationMessage";
    
    /** The Constant NOTIFICATION_PRICE. */
    private static final String NOTIFICATION_PRICE = "createNotificationPrice";
    
    /** The Constant RESULT_MESSAGE. */
    private static final String RESULT_MESSAGE = "commonAddMessage";

    /** The Constant DEFAULT_VALUE. */
    private static final int DEFAULT_VALUE = 666;

    /** The notification service. */
    @InjectService
    private NotificationService notificationService;

    /**
     * Execute.
     *
     * @param request the request
     * @return the router
     */
    @Override
    public Router execute(RequestValue request) {
        Router router = new Router();
        router.setRouteType(Router.RouteType.REDIRECT);
        router.setPagePath(PagePath.INDEX_PAGE.getJspPath());

        UnitType unitType = UnitType.valueOf(((String) request.getAttribute(RADIO_BUTTON)).toUpperCase());

        String message = (String) request.getAttribute(NOTIFICATION_MESSAGE);
        if (message != null && !message.isEmpty()) {
            message = new String(message.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        } else {
            message = String.valueOf(DEFAULT_VALUE);
        }

        String stringPrice = (String) request.getAttribute(NOTIFICATION_PRICE);
        double price = -DEFAULT_VALUE;
        if (stringPrice != null && !stringPrice.isEmpty()) {
            try {
                price = Double.parseDouble(stringPrice);
            } catch (IllegalArgumentException e) {
                LOGGER.warn("was entered incorrect price", e);
            }
        }

        User user = (User) request.getAttribute(CommonConstant.CURRENT_USER);

        BufferedImage image = null;
        try {
            image = ImageIO.read(new File((String) request.getAttribute(CommonConstant.CURRENT_NOTIFICATION_IMAGE_PATH)));
        } catch (IOException | NullPointerException e) {
            LOGGER.warn("Image was not founded", e);

            File file = new File(WebPathReturner.webPath + CommonConstant.DEFAULT_IMAGE_PATH);
            try {
                image = ImageIO.read(file);
            } catch (IOException e1) {
                LOGGER.warn("Notification image was not downloaded");
            }
        }

        try {
            Notification notification = new Notification();

            notification.setNotificationId(DEFAULT_VALUE);
            notification.setMessage(message);
            notification.setUnit(unitType);
            notification.setPrice(price);
            notification.setUserId(user.getUserId());
            notification.setDate(new Date(System.currentTimeMillis()));
            notification.setImage(image);

            notificationService.addNotification(notification);
            request.servletSessionPut(CommonConstant.INDEX_MESSAGE, ((PufarLanguage) request.getAttribute(CommonConstant.LOCALE)).getValue(RESULT_MESSAGE));
        } catch (PufarValidationException e) {
            LOGGER.warn("Were entered incorrect values", e);
            router.setPagePath(PagePath.CREATE_NOTIFICATION.getJspPath());
        } catch (PufarServiceException e) {
            LOGGER.warn("Notification was not added", e);
            router.setPagePath(PagePath.CREATE_NOTIFICATION.getJspPath());
        }

        return router;
    }

}
