package com.kozitski.pufar.controller.locale;

import com.kozitski.pufar.util.CommonConstant;
import com.kozitski.pufar.util.language.PufarLanguage;
import com.kozitski.pufar.util.language.PufarLanguageType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/locale")
public class SetEnLocaleController extends HttpServlet {

    private static final String LOCALE_TYPE = "localeType";
    private static final String LOCALE_EN = "CHANGE_LOCALE_EN";
    private static final String LOCALE_RU = "CHANGE_LOCALE_RU";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String localeType = request.getParameter(LOCALE_TYPE);

        if(localeType.equals(LOCALE_EN)){
            PufarLanguage language = (PufarLanguage) request.getSession().getAttribute(CommonConstant.LOCALE);
            language.setCurrentLanguage(PufarLanguageType.EN);

        }
        else if(localeType.equals(LOCALE_RU)){
            PufarLanguage language = (PufarLanguage) request.getSession().getAttribute(CommonConstant.LOCALE);
            language.setCurrentLanguage(PufarLanguageType.RU);
        }

        String page = (String) request.getSession().getAttribute(CommonConstant.CURRENT_PAGE);

        request.getRequestDispatcher(page).forward(request, response);
    }

}
