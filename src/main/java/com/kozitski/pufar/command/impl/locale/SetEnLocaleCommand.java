package com.kozitski.pufar.command.impl.locale;

import com.kozitski.pufar.command.AbstractCommand;
import com.kozitski.pufar.command.PagePath;
import com.kozitski.pufar.command.RequestValue;
import com.kozitski.pufar.command.Router;
import com.kozitski.pufar.util.language.PufarLanguage;
import com.kozitski.pufar.util.language.PufarLanguageType;

public class SetEnLocaleCommand extends AbstractCommand {
    private static final String LOCALE = "locale";

    @Override
    public Router execute(RequestValue request) {

        Router router = new Router();
        router.setPagePath(PagePath.INDEX_PAGE.getJspPath());

        PufarLanguage language = (PufarLanguage) request.getAttribute(LOCALE);
        language.setCurrentLanguage(PufarLanguageType.EN);

        return router;
    }

}
