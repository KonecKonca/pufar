package com.kozitski.pufar.command.impl.admin.notification.execute;

import com.kozitski.pufar.command.*;
import com.kozitski.pufar.command.request.AbstractCommand;
import com.kozitski.pufar.entity.notification.Notification;
import com.kozitski.pufar.entity.notification.NotificationParameter;
import com.kozitski.pufar.entity.notification.UnitType;
import com.kozitski.pufar.service.notification.NotificationService;
import com.kozitski.pufar.util.CommonConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SearchNotificationExecuteCommand extends AbstractCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(SearchNotificationExecuteCommand.class);

    private static final String NOTIFICATION_ID = "id";
    private static final String LOWER_PRICE = "adminLowerPrice";
    private static final String HIGHER_PRICE = "adminHigherPrice";

    private static final String PASSED_TIME = "adminPassedTime";
    private static final String SENDER_ID = "adminSenderId";
    private static final String UNIT = "adminNotificationUnit";

    private static final String LOWER_RATE = "adminLowerRate";
    private static final String HIGHER_RATE = "adminBiggerRate";

    private static final String OK_INPUT_MESSAGE = "were founded next notifications: ";
    private static final String BAD_INPUT_MESSAGE = "Were entered incorrect notifications searching attributes";

    @InjectService
    private NotificationService notificationService;

    @Override
    public Router execute(RequestValue request) {
        Router router = new Router();
        router.setPagePath(PagePath.ADMIN_CONTROL_PANEL.getJspPath());

        NotificationParameter parameters = new NotificationParameter();
        try {

            String stringId = (String) request.getAttribute(NOTIFICATION_ID);
            if (stringId != null && !stringId.isEmpty()) {
                long id = Long.parseLong(stringId);
                parameters.setNotificationId(id);
            }
            String stringLowerPrice = (String) request.getAttribute(LOWER_PRICE);
            if (stringLowerPrice != null && !stringLowerPrice.isEmpty()) {
                double lowerPrice = Double.parseDouble(stringLowerPrice);
                parameters.setLowerPrice(lowerPrice);
            }
            String stringHigherPrice = (String) request.getAttribute(HIGHER_PRICE);
            if (stringHigherPrice != null && !stringHigherPrice.isEmpty()) {
                double higherPrice = Double.parseDouble(stringHigherPrice);
                parameters.setHigherPrice(higherPrice);
            }

            String stringPassedTime = (String) request.getAttribute(PASSED_TIME);
            if (stringPassedTime != null && !stringPassedTime.isEmpty()) {
                int passedTime = Integer.parseInt(stringPassedTime);
                parameters.setPassedTime(passedTime);
            }
            String stringSenderId = (String) request.getAttribute(SENDER_ID);
            if (stringSenderId != null && !stringSenderId.isEmpty()) {
                long id = Long.parseLong(stringSenderId);
                parameters.setSenderId(id);
            }
            String stringUnit = (String) request.getAttribute(UNIT);
            if (stringUnit != null && !stringUnit.isEmpty()) {
                UnitType unit = UnitType.valueOf(stringUnit.toUpperCase());
                parameters.setUnitType(unit);
            }

            String stringLowerRate = (String) request.getAttribute(LOWER_RATE);
            if (stringLowerRate != null && !stringLowerRate.isEmpty()) {
                double lowerRate = Double.parseDouble(stringLowerRate);
                parameters.setLowerRate(lowerRate);
            }
            String stringHigherRate = (String) request.getAttribute(HIGHER_RATE);
            if (stringHigherRate != null && !stringHigherRate.isEmpty()) {
                double higherRate = Double.parseDouble(stringHigherRate);
                parameters.setHigherRate(higherRate);
            }

            request.servletSessionPut(CommonConstant.ADMIN_INPUT_MESSAGE, OK_INPUT_MESSAGE);

            List<Notification> notifications = notificationService.searchNotificationByParameters(parameters);
            request.servletSessionPut(CommonConstant.ADMIN_INPUT_RESULT, notifications);

        } catch (IllegalArgumentException | ClassCastException e) {
            LOGGER.warn(BAD_INPUT_MESSAGE, e);
            request.servletSessionPut(CommonConstant.ADMIN_INPUT_MESSAGE, BAD_INPUT_MESSAGE);
        }

        return router;
    }


}
