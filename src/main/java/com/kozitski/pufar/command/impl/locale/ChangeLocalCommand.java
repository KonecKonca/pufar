package com.kozitski.pufar.command.impl.locale;

import com.kozitski.pufar.command.request.AbstractCommand;
import com.kozitski.pufar.command.RequestValue;
import com.kozitski.pufar.command.Router;
import com.kozitski.pufar.util.CommonConstant;
import com.kozitski.pufar.util.language.PufarLanguage;
import com.kozitski.pufar.util.language.PufarLanguageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The Class ChangeLocalCommand.
 * Command for changing locale
 */
public class ChangeLocalCommand extends AbstractCommand {

    /**
     * The Constant LOGGER.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ChangeLocalCommand.class);

    /**
     * The Constant LOCALE_TYPE.
     */
    private static final String LOCALE_TYPE = "localeType";

    /**
     * The Constant LOCALE_EN.
     */
    private static final String LOCALE_EN = "CHANGE_LOCALE_EN";

    /**
     * The Constant LOCALE_RU.
     */
    private static final String LOCALE_RU = "CHANGE_LOCALE_RU";

    /**
     * Execute.
     *
     * @param requestValue the request value
     * @return the router
     */
    @Override
    public Router execute(RequestValue requestValue) {
        Router router = new Router();

        String page = (String) requestValue.getAttribute(CommonConstant.CURRENT_PAGE);
        router.setPagePath(page);

        String localeType = (String) requestValue.getAttribute(LOCALE_TYPE);

        if (localeType.equals(LOCALE_EN)) {
            PufarLanguage language = (PufarLanguage) requestValue.getAttribute(CommonConstant.LOCALE);
            language.setCurrentLanguage(PufarLanguageType.EN);
            LOGGER.info("En locale is selected");
        } else if (localeType.equals(LOCALE_RU)) {
            PufarLanguage language = (PufarLanguage) requestValue.getAttribute((CommonConstant.LOCALE));
            language.setCurrentLanguage(PufarLanguageType.RU);
            LOGGER.info("Ru locale is selected");
        }

        return router;
    }

}
