package com.kozitski.pufar.command.impl.error;

import com.kozitski.pufar.command.AbstractCommand;
import com.kozitski.pufar.command.RequestValue;
import com.kozitski.pufar.command.Router;
import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.entity.user.UserStatus;
import com.kozitski.pufar.service.error.ErrorHandleService;
import com.kozitski.pufar.util.CommonConstant;

public class ErrorCommand extends AbstractCommand {
    private ErrorHandleService service = new ErrorHandleService();

    @Override
    public Router execute(RequestValue request) {

        User currentUser = (User) request.getAttribute(CommonConstant.CURRENT_USER);
        UserStatus status = currentUser.getStatus();
        String pagePath = service.defineErrorPage(status);

        Router router = new Router();
        router.setPagePath(pagePath);

        return router;
    }

}
