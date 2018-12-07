package com.kozitski.pufar.service.error;

import com.kozitski.pufar.command.PagePath;
import com.kozitski.pufar.entity.user.UserStatus;

public class ErrorHandleService {

    public String defineErrorPage(UserStatus status){
        String pagePath = PagePath.USER_ERROR_PAGE.getJspPath();

        if(status != null && status.equals(UserStatus.SUPER_ADMIN)){
            pagePath = PagePath.ADMIN_ERROR_PAGE.getJspPath();
        }

        return pagePath;
    }

}
