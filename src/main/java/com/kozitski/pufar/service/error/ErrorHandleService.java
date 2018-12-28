package com.kozitski.pufar.service.error;

import com.kozitski.pufar.entity.user.UserStatus;

public interface ErrorHandleService {

    String defineErrorPage(UserStatus status);

}
