package com.kozitski.pufar.command.impl.profile;

import com.kozitski.pufar.command.*;
import com.kozitski.pufar.command.request.AbstractCommand;
import com.kozitski.pufar.entity.number.MobilPhoneNumber;
import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.exception.PufarServiceException;
import com.kozitski.pufar.exception.PufarValidationException;
import com.kozitski.pufar.service.number.NumberService;
import com.kozitski.pufar.util.CommonConstant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


// TODO: Auto-generated Javadoc
/**
 * The Class ChangeNumberCommand.
 */
public class ChangeNumberCommand extends AbstractCommand {
    
    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ChangeNumberCommand.class);

    /** The Constant OPERATOR. */
    private static final String OPERATOR = "operator";
    
    /** The Constant MOBILE_NUMBER. */
    private static final String MOBILE_NUMBER = "mobileNumber";
    
    /** The Constant IS_NUMBER_EXIST. */
    private static final String IS_NUMBER_EXIST = "isNumberExist";

    /** The number service. */
    @InjectService
    private NumberService numberService;


    /**
     * Execute.
     *
     * @param requestValue the request value
     * @return the router
     */
    @Override
    public Router execute(RequestValue requestValue) {
        Router router = new Router();
        router.setRouteType(Router.RouteType.REDIRECT);
        router.setPagePath(PagePath.PROFILE_PAGE.getJspPath());

        boolean isNumberExist = true;

        String isNumberExistString = (String) requestValue.getAttribute(IS_NUMBER_EXIST);
        if (isNumberExistString == null || isNumberExistString.isEmpty()) {
            isNumberExist = false;
        }

        String operator = (String) requestValue.getAttribute(OPERATOR);
        String number = (String) requestValue.getAttribute(MOBILE_NUMBER);

        User userOwner = (User) requestValue.getAttribute(CommonConstant.CURRENT_USER);
        if (userOwner != null) {
            long userOwnerId = userOwner.getUserId();

            if (operator != null && number != null) {
                MobilPhoneNumber mobilPhoneNumber = new MobilPhoneNumber();
                mobilPhoneNumber.setCountry(MobilPhoneNumber.BELARUS_CODE);
                mobilPhoneNumber.setOperator(operator);
                mobilPhoneNumber.setNumber(number);

                try {
                    numberService.changeMobilePhoneById(userOwnerId, mobilPhoneNumber, isNumberExist);
                    userOwner.setNumber(mobilPhoneNumber);

                    LOGGER.info("Number was correctly processed");
                } catch (PufarServiceException | PufarValidationException e) {
                    LOGGER.warn("number wasn't performed", e);
                }

            }
        } else {
            LOGGER.warn("Current user is equals null");
        }

        return router;
    }

}
