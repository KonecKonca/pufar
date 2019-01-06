package com.kozitski.pufar.listener;

import com.kozitski.pufar.command.PagePath;
import com.kozitski.pufar.entity.notification.Notification;
import com.kozitski.pufar.entity.notification.UnitType;
import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.entity.user.Users;
import com.kozitski.pufar.service.notification.NotificationService;
import com.kozitski.pufar.service.notification.NotificationServiceImpl;
import com.kozitski.pufar.util.CommonConstant;
import com.kozitski.pufar.util.language.PufarLanguage;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.ArrayList;
import java.util.Arrays;

@WebListener
public class SessionListener implements HttpSessionListener {
    private static final int HOW_MUCH_MESSAGES = 6;

    @Override
    public void sessionCreated(HttpSessionEvent sessionEvent) {

        //  set default page after changing locale
        sessionEvent.getSession().setAttribute(CommonConstant.CURRENT_PAGE, PagePath.TEMPLATE_PAGE.getJspPath());

        // define default user
        User defaultUser = Users.createDefaultUser();
        sessionEvent.getSession().setAttribute(CommonConstant.CURRENT_USER, defaultUser);

        // define default locale
        PufarLanguage pufarLanguage = new PufarLanguage();
        sessionEvent.getSession().setAttribute(CommonConstant.LOCALE, pufarLanguage);

        // define default messages config
        sessionEvent.getSession().setAttribute(CommonConstant.HOW_MUCH_MESSAGES, HOW_MUCH_MESSAGES);

        //search top notifications
        NotificationService notificationService = new NotificationServiceImpl();
        ArrayList<Notification> topNotifications = notificationService.searchTopNotificationsWithLimit(CommonConstant.HOW_MUCH_NOTIFICATIONS);
        sessionEvent.getSession().setAttribute(CommonConstant.CURRENT_NOTIFICATIONS, topNotifications);

        // define all units
        ArrayList<UnitType> units = new ArrayList<>(Arrays.asList(UnitType.values()));
        sessionEvent.getSession().setAttribute(CommonConstant.UNIT_LIST, units);

    }

}
