package com.kozitski.pufar.service.user;

import com.kozitski.pufar.command.RequestValue;
import com.kozitski.pufar.dao.number.NumberDao;
import com.kozitski.pufar.dao.user.UserDao;
import com.kozitski.pufar.entity.number.MobilPhoneNumber;
import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.entity.user.UserParameter;
import com.kozitski.pufar.entity.user.UserStatus;
import com.kozitski.pufar.exception.PufarDAOException;
import com.kozitski.pufar.exception.PufarServiceException;
import com.kozitski.pufar.exception.PufarValidationException;
import com.kozitski.pufar.service.AbstractService;
import com.kozitski.pufar.service.InjectDao;
import com.kozitski.pufar.util.CommonConstant;
import com.kozitski.pufar.util.encoder.PasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl extends AbstractService implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private static final int BAN_STATUS_TRUE = 1;
    private static final int BAN_STATUS_FALSE = 0;

    @InjectDao
    private UserDao userDao;
    @InjectDao
    private NumberDao numberDao;

    public Optional<User> searchUserById(long id) {
        Optional<User> user = userDao.searchById(id);

        if (user.isPresent()) {
            User findUser = user.get();
            Optional<MobilPhoneNumber> mobilPhoneNumber = numberDao.searchById(findUser.getUserId());

            if (mobilPhoneNumber.isPresent()) {
                MobilPhoneNumber findNumber = mobilPhoneNumber.get();
                findUser.setNumber(findNumber);
            }
        }

        return user;
    }

    @Override
    public Optional<User> searchUserByLogin(String login) throws PufarValidationException {
        // any validation
        return userDao.searchUserByLogin(login);
    }

    @Override
    public List<User> searchUsersByStatus(UserStatus status) {

        // any validation
        return userDao.searchUsersByStatus(status);
    }

    @Override
    public User addUser(String login, String password) throws PufarServiceException, PufarValidationException {
        User user = new User();

        String utf8Login = new String(login.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        String utf8Password = new String(password.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

        user.setLogin(utf8Login);
        user.setPassword(PasswordEncoder.encode(utf8Password));
        user.setStatus(UserStatus.SIMPLE_USER);

        try {
            userDao.addUser(user);
            return user;
        } catch (PufarDAOException e) {
            throw new PufarServiceException("Registration is failed", e);
        }

    }

    @Override
    public List<User> searchUsersByParameters(UserParameter parameter) {
        return userDao.searchByParameters(parameter);
    }

    @Override
    public boolean banUserById(long id, User currentUser) {
        return userDao.insertBanStatus(id, currentUser, BAN_STATUS_TRUE);
    }

    @Override
    public boolean unBanUserById(long id, User currentUser) {
        return userDao.insertBanStatus(id, currentUser, BAN_STATUS_FALSE);
    }

    @Override
    public boolean changeUserLogin(long id, String newLogin, User currentUser) {
        return userDao.changeUserLogin(id, newLogin, currentUser);
    }

    @Override
    public boolean changeUserStatusByUserId(long id, String newStatus, User currentUser) {
        boolean result = false;

        try {
            UserStatus userStatus = UserStatus.valueOf(newStatus.toUpperCase());
            result = userDao.changeUserStatusByUserId(id, userStatus, currentUser);
        } catch (IllegalArgumentException e) {
            LOGGER.warn("Was entered incorrect user status", e);
        }

        return result;
    }

    @Override
    public void changePassword(RequestValue requestValue, long userId, String oldPassword, String newPassword, String newPasswordConfirm) throws PufarServiceException, PufarValidationException {
        boolean resultMessage = true;

        String oldEncodedPassword = PasswordEncoder.encode(oldPassword);
        String encodeNewPassword = PasswordEncoder.encode(newPassword);

        Optional<User> optionalUser = userDao.searchById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            if (oldEncodedPassword.trim().equals(user.getPassword().trim()) && newPassword.equals(newPasswordConfirm)
                    && !oldEncodedPassword.equals(encodeNewPassword)) {

                try {
                    userDao.changePassword(userId, encodeNewPassword);
                } catch (PufarDAOException e) {
                    requestValue.requestAttributePut(CommonConstant.CHANGE_PASSWORD_MESSAGE, false);
                    throw new PufarServiceException(e);
                }
            } else {
                resultMessage = false;
            }
        } else {
            resultMessage = false;
        }

        requestValue.requestAttributePut(CommonConstant.CHANGE_PASSWORD_MESSAGE, resultMessage);
    }

}