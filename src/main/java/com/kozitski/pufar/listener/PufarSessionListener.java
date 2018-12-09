package com.kozitski.pufar.listener;

import com.kozitski.pufar.entity.notification.Notification;
import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.entity.user.Users;
import com.kozitski.pufar.service.notification.NotificationService;
import com.kozitski.pufar.service.notification.NotificationServiceImpl;
import com.kozitski.pufar.util.CommonConstant;
import com.kozitski.pufar.util.language.PufarLanguage;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.List;

@WebListener
public class PufarSessionListener implements HttpSessionListener {
    private static final String CURRENT_USER = "currentUser";
    private static final String LOCALE = "locale";

    private static final int HOW_MUCH_MESSAGES = 6;
    private static final int HOW_MUCH_NOTIFICATIONS = 10;

    private NotificationService notificationService = new NotificationServiceImpl();

    @Override
    public void sessionCreated(HttpSessionEvent sessionEvent) {

        // define default user
        User defaultUser = Users.createDefaultUser();
        sessionEvent.getSession().setAttribute(CURRENT_USER, defaultUser);

        // define default locale
        PufarLanguage pufarLanguage = new PufarLanguage();
        sessionEvent.getSession().setAttribute(LOCALE, pufarLanguage);

        // define default messages config
        sessionEvent.getSession().setAttribute(CommonConstant.HOW_MUCH_MESSAGES, HOW_MUCH_MESSAGES);

        //search top notifications
        List<Notification> topNotifications = notificationService.searchTopNotificationsWithLimit(HOW_MUCH_NOTIFICATIONS);
        sessionEvent.getSession().setAttribute(CommonConstant.CURRENT_NOTIFICATIONS, topNotifications);

    }


}
