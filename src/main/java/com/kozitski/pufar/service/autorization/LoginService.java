package com.kozitski.pufar.service.autorization;

import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.exception.PufarValidationException;
import com.kozitski.pufar.validation.annotation.AspectValid;
import com.kozitski.pufar.validation.annotation.primitive.string.StringValid;

import java.util.Optional;

// TODO: Auto-generated Javadoc
/**
 * The Interface LoginService.
 */
public interface LoginService {

    /**
     * Search user by login password.
     *
     * @param login the login
     * @param password the password
     * @return the optional
     * @throws PufarValidationException the pufar validation exception
     */
    @AspectValid
    Optional<User> searchUserByLoginPassword(@StringValid(minLength = 4, maxLength = 16) String login,
                                       @StringValid(minLength = 4, maxLength = 60) String password) throws PufarValidationException;



}
