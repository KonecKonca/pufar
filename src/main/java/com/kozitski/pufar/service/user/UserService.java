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

public interface UserService {
    Optional<User> searchUserById(long id);

    @AspectValid
    Optional<User> searchUserByLogin(@StringValid String login) throws PufarValidationException;

    @AspectValid
    User addUser(@StringValid(minLength = 4, maxLength = 20) String login,
                 @StringValid(minLength = 5, maxLength = 20) String password) throws PufarServiceException, PufarValidationException;

    List<User> searchUsersByStatus(UserStatus status);

    List<User> searchUsersByParameters(UserParameter parameter);

    boolean banUserById(long id, User currentUser);

    boolean unBanUserById(long id, User currentUser);

    boolean changeUserLogin(long id, String newLogin, User currentUser);

    boolean changeUserStatusByUserId(long id, String newStatus, User currentUser);

    @AspectValid
    void changePassword(RequestValue requestValue, long userId,
                        @StringValid(minLength = 5, maxLength = 60) String oldPassword,
                        @StringValid(minLength = 5, maxLength = 60) String newPassword,
                        @StringValid(minLength = 5, maxLength = 60) String newPasswordConfirm) throws PufarServiceException, PufarValidationException;

}
