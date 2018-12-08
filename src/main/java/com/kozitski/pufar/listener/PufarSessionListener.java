package com.kozitski.pufar.listener;

import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.entity.user.Users;
import com.kozitski.pufar.util.CommonConstant;
import com.kozitski.pufar.util.language.PufarLanguage;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class PufarSessionListener implements HttpSessionListener {
    private static final String CURRENT_USER = "currentUser";
    private static final String LOCALE = "locale";

    private static final Integer HOW_MUCH_MESSAGES = 6;

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


    }


}
