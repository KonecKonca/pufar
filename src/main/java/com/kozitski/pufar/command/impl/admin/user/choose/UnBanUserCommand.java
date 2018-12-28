package com.kozitski.pufar.command.impl.admin.user.choose;

import com.kozitski.pufar.command.*;
import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.service.user.UserService;
import com.kozitski.pufar.service.user.UserServiceImpl;
import com.kozitski.pufar.util.CommonConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UnBanUserCommand extends AbstractCommand {
    private static Logger LOGGER = LoggerFactory.getLogger(UnBanUserCommand.class);
    private static final String USER_ID = "id";

    private static final String OK_INPUT_MESSAGE = "user was UnBanned ";
    private static final String BAD_INPUT_MESSAGE = "was entered incorrect user ID to UNBAN user";

    @InjectService
    private UserService userService;

    @Override
    public Router execute(RequestValue request) {
        Router router = new Router();
        router.setPagePath(PagePath.ADMIN_CONTROL_PANEL.getJspPath());

        try {
            long id = Long.parseLong(request.getAttribute(USER_ID).toString());
            User currentUser = (User) request.getAttribute(CommonConstant.CURRENT_USER);

            if(userService.unBanUserById(id, currentUser)){
                request.requestAttributePut(CommonConstant.ADMIN_INPUT_MESSAGE, OK_INPUT_MESSAGE);
            }
            else {
                request.requestAttributePut(CommonConstant.ADMIN_INPUT_MESSAGE, BAD_INPUT_MESSAGE);
            }
        }
        catch (NumberFormatException e){
            LOGGER.warn(BAD_INPUT_MESSAGE, e);
            request.requestAttributePut(CommonConstant.ADMIN_INPUT_MESSAGE, BAD_INPUT_MESSAGE);
        }

        return router;
    }

}
