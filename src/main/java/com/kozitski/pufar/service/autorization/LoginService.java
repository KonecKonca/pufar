package com.kozitski.pufar.service.autorization;

import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.exception.PufarDAOException;
import com.kozitski.pufar.validation.annotation.AspectValid;
import com.kozitski.pufar.validation.annotation.primitive.string.StringValid;

import java.util.Optional;

public interface LoginService {

    @AspectValid
    Optional<User> searchUserByLoginPassword(@StringValid(minLength = 4, maxLength = 16) String login,
                                       @StringValid(minLength = 4, maxLength = 16) String password);



}
