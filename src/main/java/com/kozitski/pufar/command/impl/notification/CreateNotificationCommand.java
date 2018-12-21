package com.kozitski.pufar.command.impl.notification;

import com.kozitski.pufar.command.AbstractCommand;
import com.kozitski.pufar.command.PagePath;
import com.kozitski.pufar.command.RequestValue;
import com.kozitski.pufar.command.Router;
import com.kozitski.pufar.entity.notification.Notification;
import com.kozitski.pufar.entity.notification.UnitType;
import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.service.notification.NotificationService;
import com.kozitski.pufar.service.notification.NotificationServiceImpl;
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

public class CreateNotificationCommand extends AbstractCommand {
    private static Logger LOGGER = LoggerFactory.getLogger(CreateNotificationCommand.class);
    private NotificationService notificationService = new NotificationServiceImpl();

    private static final String RADIO_BUTTON1 = "radios1";
    private static final String RADIO_BUTTON2 = "radios2";
    private static final String RADIO_BUTTON3 = "radios3";
    private static final String RADIO_BUTTON4 = "radios4";
    private static final String RADIO_BUTTON5 = "radios5";
    private static final String RADIO_BUTTON6 = "radios6";
    private static final String RADIO_BUTTON7 = "radios7";
    private static final String RADIO_BUTTON8 = "radios8";

    private static final String NOTIFICATION_MESSAGE = "createNotificationMessage";
    private static final String NOTIFICATION_PRICE = "createNotificationPrice";
    private static final String RESULT_MESSAGE = "commonAddMessage";

    private static final int DEFAULT_VALUE = 666;

    @Override
    public Router execute(RequestValue request) {
        Router router = new Router();
        router.setPagePath(PagePath.INDEX_PAGE.getJspPath());

        UnitType unitType = defineUnitType(
                Boolean.parseBoolean((String) request.getAttribute(RADIO_BUTTON1)),  Boolean.parseBoolean((String) request.getAttribute(RADIO_BUTTON2)),
                Boolean.parseBoolean((String) request.getAttribute(RADIO_BUTTON3)),  Boolean.parseBoolean((String) request.getAttribute(RADIO_BUTTON4)),
                Boolean.parseBoolean((String) request.getAttribute(RADIO_BUTTON5)), Boolean.parseBoolean((String) request.getAttribute(RADIO_BUTTON6)),
                Boolean.parseBoolean((String) request.getAttribute(RADIO_BUTTON7)), Boolean.parseBoolean((String) request.getAttribute(RADIO_BUTTON8)));

        System.out.println(unitType);

        String message = (String) request.getAttribute(NOTIFICATION_MESSAGE);
        if(message != null && !message.isEmpty()){
            message = new String(message.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        }
        else {
            message = String.valueOf(DEFAULT_VALUE);
        }

        String stringPrice = (String) request.getAttribute(NOTIFICATION_PRICE);
        double price = 0;
        if(stringPrice != null && !stringPrice.isEmpty()){
            price = Double.parseDouble(stringPrice);
        }

        User user = (User) request.getAttribute(CommonConstant.CURRENT_USER);

        BufferedImage image = null;
        try {
            image = ImageIO.read(new File((String) request.getAttribute(CommonConstant.CURRENT_NOTIFICATION_IMAGE_PATH)));
        }
        catch (IOException | NullPointerException e) {
            LOGGER.warn("Image was not founded", e);

            File file = new File(WebPathReturner.webPath + CommonConstant.DEFAULT_IMAGE_PATH);
            try {
                image = ImageIO.read(file);
            }
            catch (IOException e1) {
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
            request.requestAttributePut(CommonConstant.INDEX_MESSAGE, ((PufarLanguage)request.getAttribute(CommonConstant.LOCALE)).getValue(RESULT_MESSAGE));
        }
        catch (Exception e){
            LOGGER.warn("Notification was not added", e);
            router.setPagePath(PagePath.CREATE_NOTIFICATION.getJspPath());
        }

        return router;
    }

    private UnitType defineUnitType(boolean ... variants){
        UnitType result = UnitType.OTHER;

        for (int i = 0; i < variants.length; i++) {
            if(variants[i]){
                for(UnitType type : UnitType.values()){
                    if(type.ordinal() == i){
                        result = type;
                    }
                }
            }
        }

        return result;
    }

}
