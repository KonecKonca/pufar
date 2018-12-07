package com.kozitski.pufar.dao.user;

import com.kozitski.pufar.dao.PufarDao;
import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.entity.user.UserStatus;
import com.kozitski.pufar.exception.PufarDAOException;
import com.kozitski.pufar.validation.annotation.AspectValid;
import com.kozitski.pufar.validation.annotation.primitive.integer.IntValid;
import com.kozitski.pufar.validation.annotation.primitive.string.StringValid;
import com.kozitski.pufar.validation.annotation.user.UserValid;

import java.util.ArrayList;
import java.util.Optional;

public interface UserDao extends PufarDao<User>{

    @Override
    Optional<User> searchById(long id);

    Optional<User> searchUserByLogin(String login);
    ArrayList<User> searchUsersByStatus(UserStatus status);

    User addUser(User user) throws PufarDAOException;

}
