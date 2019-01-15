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
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving session events.
 * The class that is interested in processing a session
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addSessionListener<code> method. When
 * the session event occurs, that object's appropriate
 * method is invoked.
 *
 * @see SessionEvent
 */
@WebListener
public class SessionListener implements HttpSessionListener {
    
    /** The Constant HOW_MUCH_MESSAGES. */
    private static final int HOW_MUCH_MESSAGES = 5;

    /**
     * Invoked when session is created.
     *
     * @param sessionEvent the session event
     */
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
        List<Notification> topNotifications = notificationService.searchTopNotificationsWithLimit(CommonConstant.HOW_MUCH_NOTIFICATIONS);
        sessionEvent.getSession().setAttribute(CommonConstant.CURRENT_NOTIFICATIONS, topNotifications);

        // define all units
        ArrayList<UnitType> units = new ArrayList<>(Arrays.asList(UnitType.values()));
        sessionEvent.getSession().setAttribute(CommonConstant.UNIT_LIST, units);

    }

}
