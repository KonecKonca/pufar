package com.kozitski.pufar.command.impl.profile;

import com.kozitski.pufar.command.*;
import com.kozitski.pufar.command.request.AbstractCommand;
import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.service.user.UserService;
import com.kozitski.pufar.util.CommonConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

/**
 * The Class ChangePasswordCommand.
 * Command for changing password
 */
public class ChangePasswordCommand extends AbstractCommand {
    
    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ChangePasswordCommand.class);

    /** The Constant OLD_PASSWORD. */
    private static final String OLD_PASSWORD = "oldPassword";
    
    /** The Constant NEW_PASSWORD. */
    private static final String NEW_PASSWORD = "newPassword";
    
    /** The Constant NEW_PASSWORD_CONFIRM. */
    private static final String NEW_PASSWORD_CONFIRM = "newPasswordConfirm";

    /** The user service. */
    @InjectService
    private UserService userService;

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

        User currentUser = (User) requestValue.getAttribute(CommonConstant.CURRENT_USER);
        long currentUserId = currentUser.getUserId();

        String oldPassword = (String) requestValue.getAttribute(OLD_PASSWORD);
        String newPassword = (String) requestValue.getAttribute(NEW_PASSWORD);
        String newPasswordConfirm = (String) requestValue.getAttribute(NEW_PASSWORD_CONFIRM);

        String oldPasswordUtf8 = new String(oldPassword.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        String newPasswordUtf8 = new String(newPassword.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        String newPasswordConfirmUtf8 = new String(newPasswordConfirm.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

        try {
            userService.changePassword(requestValue, currentUserId, oldPasswordUtf8, newPasswordUtf8, newPasswordConfirmUtf8);
        } catch (Exception e) {
            requestValue.requestAttributePut(CommonConstant.CHANGE_PASSWORD_MESSAGE, false);
            LOGGER.warn("Password wasn't changed");
        }

        return router;
    }


}
