package com.kozitski.pufar.listener;

import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.entity.user.Users;
import com.kozitski.pufar.util.language.PufarLanguage;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class PufarSessionListener implements HttpSessionListener {
    private static final String CURRENT_USER = "currentUser";
    private static final String LOCALE = "locale";

    @Override
    public void sessionCreated(HttpSessionEvent sessionEvent) {

        User defaultUser = Users.createDefaultUser();
        sessionEvent.getSession().setAttribute(CURRENT_USER, defaultUser);

        PufarLanguage pufarLanguage = new PufarLanguage();
        sessionEvent.getSession().setAttribute(LOCALE, pufarLanguage);

    }


}
