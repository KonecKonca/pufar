package com.kozitski.pufar.service.user;

import com.google.common.collect.Lists;
import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.entity.user.UserParameter;
import com.kozitski.pufar.entity.user.UserStatus;
import com.kozitski.pufar.exception.PufarServiceException;
import com.kozitski.pufar.validation.annotation.AspectValid;
import com.kozitski.pufar.validation.annotation.primitive.string.StringValid;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface UserService {
    String PASSWORD_REGEXP = ".*";
    String LOGIN_REGEXP = ".*";


    Optional<User> searchUserById(long id);
    @AspectValid
    Optional<User> searchUserByLogin(@StringValid String login);
    @AspectValid
    User addUser(@StringValid(minLength = 4, maxLength = 20, validateRegExp = LOGIN_REGEXP) String login,
                 @StringValid(minLength = 5, maxLength = 20, validateRegExp = PASSWORD_REGEXP) String password) throws PufarServiceException;

    List<User> searchUsersByStatus(UserStatus status);
    ArrayList<User> searchUsersByParameters(UserParameter parameter);

    boolean banUserById(long id, User currentUser);
    boolean unBanUserById(long id, User currentUser);
    boolean changeUserLogin(long id, String newLogin, User currentUser);
    boolean changeUserStatusByUserId(long id, String newStatus, User currentUser);

}
