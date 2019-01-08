package com.kozitski.pufar.command.impl.profile;

import com.kozitski.pufar.command.*;
import com.kozitski.pufar.command.request.AbstractCommand;
import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.service.user.UserService;
import com.kozitski.pufar.util.CommonConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

public class ChangePasswordCommand extends AbstractCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChangePasswordCommand.class);

    private static final String OLD_PASSWORD = "oldPassword";
    private static final String NEW_PASSWORD = "newPassword";
    private static final String NEW_PASSWORD_CONFIRM = "newPasswordConfirm";

    @InjectService
    private UserService userService;

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
