package com.kozitski.pufar.service.user;

import com.kozitski.pufar.command.impl.autorization.RegistrationCommand;
import com.kozitski.pufar.dao.user.MySQLUserDao;
import com.kozitski.pufar.dao.user.UserDao;
import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.entity.user.UserStatus;
import com.kozitski.pufar.exception.PufarDAOException;
import com.kozitski.pufar.exception.PufarServiceException;
import com.kozitski.pufar.util.encoder.PasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserDao userDao = new MySQLUserDao();

    public Optional<User> searchUserById(long id){
        // any validation
        return userDao.searchById(id);
    }
    @Override
    public Optional<User> searchUserByLogin(String login) {
        // any validation
        return userDao.searchUserByLogin(login);
    }
    @Override
    public ArrayList<User> searchUsersByStatus(UserStatus status) {

        // any validation
        return userDao.searchUsersByStatus(status);
    }

    @Override
    public User addUser(String login, String password) throws PufarServiceException {
        User user = new User();

        String utf8Login = new String(login.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        String utf8Password = new String(password.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

        user.setLogin(utf8Login);
        user.setPassword(PasswordEncoder.encode(utf8Password));
        user.setStatus(UserStatus.SIMPLE_USER);

        try {
            userDao.addUser(user);
            return user;
        }
        catch (PufarDAOException e) {
            throw new PufarServiceException("Registration is failed", e);
        }

    }


}
