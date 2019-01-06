package com.kozitski.pufar.dao.user;

import com.kozitski.pufar.dao.PufarDao;
import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.entity.user.UserParameter;
import com.kozitski.pufar.entity.user.UserStatus;
import com.kozitski.pufar.exception.PufarDAOException;

import java.util.List;
import java.util.Optional;

public interface UserDao extends PufarDao<User, UserParameter> {

    @Override
    Optional<User> searchById(long id);

    Optional<User> searchUserByLogin(String login);

    List<User> searchUsersByStatus(UserStatus status);

    User addUser(User user) throws PufarDAOException;

    @Override
    List<User> searchByParameters(UserParameter parameter);

    boolean insertBanStatus(long userId, User currentUser, int banStatus);

    boolean changeUserLogin(long id, String newLogin, User currentUser);

    boolean changeUserStatusByUserId(long id, UserStatus newStatus, User currentUser);

    void changePassword(long userId, String newPassword) throws PufarDAOException;

}
