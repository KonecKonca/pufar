package com.kozitski.pufar.dao.user;

import com.kozitski.pufar.dao.PufarDao;
import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.entity.user.UserParameter;
import com.kozitski.pufar.entity.user.UserStatus;
import com.kozitski.pufar.exception.PufarDAOException;

import java.util.List;
import java.util.Optional;

// TODO: Auto-generated Javadoc
/**
 * The Interface UserDao.
 */
public interface UserDao extends PufarDao<User, UserParameter> {

    /**
     * Search by id.
     *
     * @param id the id
     * @return the optional
     */
    @Override
    Optional<User> searchById(long id);

    /**
     * Search user by login.
     *
     * @param login the login
     * @return the optional
     */
    Optional<User> searchUserByLogin(String login);

    /**
     * Search users by status.
     *
     * @param status the status
     * @return the list
     */
    List<User> searchUsersByStatus(UserStatus status);

    /**
     * Adds the user.
     *
     * @param user the user
     * @return the user
     * @throws PufarDAOException the pufar DAO exception
     */
    User addUser(User user) throws PufarDAOException;

    /**
     * Search by parameters.
     *
     * @param parameter the parameter
     * @return the list
     */
    @Override
    List<User> searchByParameters(UserParameter parameter);

    /**
     * Insert ban status.
     *
     * @param userId the user id
     * @param currentUser the current user
     * @param banStatus the ban status
     * @return true, if successful
     */
    boolean insertBanStatus(long userId, User currentUser, int banStatus);

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
    boolean changeUserStatusByUserId(long id, UserStatus newStatus, User currentUser);

    /**
     * Change password.
     *
     * @param userId the user id
     * @param newPassword the new password
     * @throws PufarDAOException the pufar DAO exception
     */
    void changePassword(long userId, String newPassword) throws PufarDAOException;

}
