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

import java.util.List;
import java.util.Optional;

/**
 * The Class UserServiceImpl.
 */
public class UserServiceImpl extends AbstractService implements UserService {
    
    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    
    /** The Constant BAN_STATUS_TRUE. */
    private static final int BAN_STATUS_TRUE = 1;
    
    /** The Constant BAN_STATUS_FALSE. */
    private static final int BAN_STATUS_FALSE = 0;

    /** The user dao. */
    @InjectDao
    private UserDao userDao;
    
    /** The number dao. */
    @InjectDao
    private NumberDao numberDao;

    /**
     * Search user by id.
     *
     * @param id the id
     * @return the optional
     */
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

    /**
     * Search user by login.
     *
     * @param login the login
     * @return the optional
     * @throws PufarValidationException the pufar validation exception
     */
    @Override
    public Optional<User> searchUserByLogin(String login) throws PufarValidationException {
        return userDao.searchUserByLogin(login);
    }

    /**
     * Search users by status.
     *
     * @param status the status
     * @return the list
     */
    @Override
    public List<User> searchUsersByStatus(UserStatus status) {

        // any validation
        return userDao.searchUsersByStatus(status);
    }

    /**
     * Adds the user.
     *
     * @param login the login
     * @param password the password
     * @return the user
     * @throws PufarServiceException the pufar service exception
     * @throws PufarValidationException the pufar validation exception
     */
    @Override
    public User addUser(String login, String password) throws PufarServiceException, PufarValidationException {
        User user = new User();

        user.setLogin(login);
        user.setPassword(PasswordEncoder.encode(password));
        user.setStatus(UserStatus.SIMPLE_USER);

        try {
            userDao.addUser(user);
            return user;
        } catch (PufarDAOException e) {
            throw new PufarServiceException("Registration is failed", e);
        }

    }

    /**
     * Search users by parameters.
     *
     * @param parameter the parameter
     * @return the list
     */
    @Override
    public List<User> searchUsersByParameters(UserParameter parameter) {
        return userDao.searchByParameters(parameter);
    }

    /**
     * Ban user by id.
     *
     * @param id the id
     * @param currentUser the current user
     * @return true, if successful
     */
    @Override
    public boolean banUserById(long id, User currentUser) {
        return userDao.insertBanStatus(id, currentUser, BAN_STATUS_TRUE);
    }

    /**
     * Un ban user by id.
     *
     * @param id the id
     * @param currentUser the current user
     * @return true, if successful
     */
    @Override
    public boolean unBanUserById(long id, User currentUser) {
        return userDao.insertBanStatus(id, currentUser, BAN_STATUS_FALSE);
    }

    /**
     * Change user login.
     *
     * @param id the id
     * @param newLogin the new login
     * @param currentUser the current user
     * @return true, if successful
     */
    @Override
    public boolean changeUserLogin(long id, String newLogin, User currentUser) {
        return userDao.changeUserLogin(id, newLogin, currentUser);
    }

    /**
     * Change user status by user id.
     *
     * @param id the id
     * @param newStatus the new status
     * @param currentUser the current user
     * @return true, if successful
     */
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