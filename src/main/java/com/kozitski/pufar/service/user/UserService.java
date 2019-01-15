package com.kozitski.pufar.service.user;

import com.kozitski.pufar.command.RequestValue;
import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.entity.user.UserParameter;
import com.kozitski.pufar.entity.user.UserStatus;
import com.kozitski.pufar.exception.PufarServiceException;
import com.kozitski.pufar.exception.PufarValidationException;
import com.kozitski.pufar.validation.annotation.AspectValid;
import com.kozitski.pufar.validation.annotation.primitive.string.StringValid;

import java.util.List;
import java.util.Optional;

/**
 * The Interface UserService.
 */
public interface UserService {
    
    /**
     * Search user by id.
     *
     * @param id the id
     * @return the optional
     */
    Optional<User> searchUserById(long id);

    /**
     * Search user by login.
     *
     * @param login the login
     * @return the optional
     * @throws PufarValidationException the pufar validation exception
     */
    @AspectValid
    Optional<User> searchUserByLogin(@StringValid String login) throws PufarValidationException;

    /**
     * Adds the user.
     *
     * @param login the login
     * @param password the password
     * @return the user
     * @throws PufarServiceException the pufar service exception
     * @throws PufarValidationException the pufar validation exception
     */
    @AspectValid
    User addUser(@StringValid(minLength = 4, maxLength = 20) String login,
                 @StringValid(minLength = 5, maxLength = 20) String password) throws PufarServiceException, PufarValidationException;

    /**
     * Search users by status.
     *
     * @param status the status
     * @return the list
     */
    List<User> searchUsersByStatus(UserStatus status);

    /**
     * Search users by parameters.
     *
     * @param parameter the parameter
     * @return the list
     */
    List<User> searchUsersByParameters(UserParameter parameter);

    /**
     * Ban user by id.
     *
     * @param id the id
     * @param currentUser the current user
     * @return true, if successful
     */
    boolean banUserById(long id, User currentUser);

    /**
     * Un ban user by id.
     *
     * @param id the id
     * @param currentUser the current user
     * @return true, if successful
     */
    boolean unBanUserById(long id, User currentUser);

    /**
     * Change user login.
     *
     * @param id the id
     * @param newLogin the new login
     * @param currentUser the current user
     * @return true, if successful
     */
    boolean changeUserLogin(long id, String newLogin, User currentUser);

    /**
     * Change user status by user id.
     *
     * @param id the id
     * @param newStatus the new status
     * @param currentUser the current user
     * @return true, if successful
     */
    boolean changeUserStatusByUserId(long id, String newStatus, User currentUser);

    /**
     * Change password.
     *
     * @param requestValue the request value
     * @param userId the user id
     * @param oldPassword the old password
     * @param newPassword the new password
     * @param newPasswordConfirm the new password confirm
     * @throws PufarServiceException the pufar service exception
     * @throws PufarValidationException the pufar validation exception
     */
    @AspectValid
    void changePassword(RequestValue requestValue, long userId,
                        @StringValid(minLength = 5, maxLength = 60) String oldPassword,
                        @StringValid(minLength = 5, maxLength = 60) String newPassword,
                        @StringValid(minLength = 5, maxLength = 60) String newPasswordConfirm) throws PufarServiceException, PufarValidationException;

}
