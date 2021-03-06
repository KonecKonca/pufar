package com.kozitski.pufar.service.error;

import com.kozitski.pufar.entity.user.UserStatus;

/**
 * The Interface ErrorHandleService.
 */
public interface ErrorHandleService {

    /**
     * Define error page.
     *
     * @param status the status
     * @return the string
     */
    String defineErrorPage(UserStatus status);

}
