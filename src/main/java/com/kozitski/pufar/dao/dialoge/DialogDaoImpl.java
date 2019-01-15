package com.kozitski.pufar.dao.dialoge;

import com.kozitski.pufar.database.ConnectionPool;
import com.kozitski.pufar.dao.PufarDaoConstant;
import com.kozitski.pufar.entity.message.UserMessage;
import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.exception.PufarDAOException;
import com.kozitski.pufar.util.mapper.dialog.DialogMapper;
import com.kozitski.pufar.util.mapper.user.UserMapper;
import org.apache.commons.dbutils.DbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class DialogDaoImpl.
 * Implementation of DialogDao on MySQL
 */
public class DialogDaoImpl implements DialogDao {
    
    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(DialogDaoImpl.class);
    
    /** The Constant SEARCH_POPULAR_USERS_SQL. */
    private static final String SEARCH_POPULAR_USERS_SQL =
            "SELECT user_id, login, password, status, ban_status isBanned, MAX(dialog_number) max_number " +
                "FROM( " +
                    "SELECT u.user_id, u.login, u.password, u.status, u.ban_status, MAX(d.dialog_id) dialog_number FROM users u " +
                        "INNER JOIN dialoges d ON u.user_id = d.user_receiver_id " +
                            "WHERE u.user_id IN( " +

                                "SELECT u.user_id FROM users u " +
                                    "WHERE u.user_id IN( " +

                                        "SELECT d1.user_receiver_id FROM users u1 " +
                                            "INNER JOIN dialoges d1 ON u1.user_id = d1.user_sender_id " +
                                                "WHERE d1.user_sender_id=? " +

                                        "UNION " +

                                        "SELECT d2.user_sender_id FROM users u2 " +
                                            "INNER JOIN dialoges d2 ON u2.user_id = d2.user_receiver_id " +
                                                "WHERE d2.user_receiver_id=?" +
                                    ") " +
                                 ") " +
                                "AND d.user_sender_id=? " +
                            "GROUP BY u.user_id " +

                            "UNION " +

                            "SELECT u.user_id, u.login, u.password, u.status, u.ban_status, MAX(d.dialog_id) FROM users u " +
                                "INNER JOIN dialoges d ON u.user_id = d.user_sender_id " +
                                    "WHERE u.user_id IN( " +

                                        "SELECT u.user_id FROM users u " +
                                            "WHERE u.user_id IN( " +

                                                "SELECT d1.user_receiver_id FROM users u1 " +
                                                    "INNER JOIN dialoges d1 ON u1.user_id = d1.user_sender_id " +
                                                        "WHERE d1.user_sender_id=? " +

                                                "UNION " +

                                                "SELECT d2.user_sender_id FROM users u2 " +
                                                    "INNER JOIN dialoges d2 ON u2.user_id = d2.user_receiver_id " +
                                                        "WHERE d2.user_receiver_id=? " +

                                            ") " +
                                        ") " +
                                        "AND d.user_receiver_id=? " +
                            "GROUP BY u.user_id " +
                ") t "+

            "GROUP BY user_id " +
            "ORDER BY max_number DESC " +
            "LIMIT ?";

    /** The Constant SEARCH_MESSAGE_FROM_TO_SQL. */
    private static final String SEARCH_MESSAGE_FROM_TO_SQL =
            "SELECT u1.login sender_login, u2.login receiver_login, message, date FROM dialoges d LEFT JOIN users u1 ON d.user_sender_id = u1.user_id " +
                "LEFT JOIN users u2 ON d.user_receiver_id = u2.user_id WHERE d.user_sender_id = ? AND d.user_receiver_id = ?";
    
    /** The Constant SEARCH_MESSAGE_BETWEEN_SQL. */
    private static final String SEARCH_MESSAGE_BETWEEN_SQL =
            "SELECT u1.login sender_login, u2.login receiver_login, message, date FROM dialoges d " +
                "LEFT JOIN users u1 ON d.user_sender_id = u1.user_id " +
                "LEFT JOIN users u2 ON d.user_receiver_id = u2.user_id WHERE d.user_sender_id = ? AND d.user_receiver_id = ? " +
                    "UNION " +
            "SELECT u3.login sender_login, u4.login receiver_login, message, date FROM dialoges d " +
                "LEFT JOIN users u3 ON d.user_sender_id = u3.user_id " +
                "LEFT JOIN users u4 ON d.user_receiver_id = u4.user_id WHERE d.user_sender_id = ? AND d.user_receiver_id = ? " +

            "ORDER BY date ASC";
    
    /** The Constant SEARCH_MESSAGE_BETWEEN_WITH_LIMIT_SQL. */
    private static final String SEARCH_MESSAGE_BETWEEN_WITH_LIMIT_SQL =
            "SELECT u1.login sender_login, u2.login receiver_login, message, date FROM dialoges d " +
                "LEFT JOIN users u1 ON d.user_sender_id = u1.user_id " +
                "LEFT JOIN users u2 ON d.user_receiver_id = u2.user_id WHERE d.user_sender_id = ? AND d.user_receiver_id = ? " +
                    "UNION " +
            "SELECT u3.login sender_login, u4.login receiver_login, message, date FROM dialoges d " +
                "LEFT JOIN users u3 ON d.user_sender_id = u3.user_id " +
                "LEFT JOIN users u4 ON d.user_receiver_id = u4.user_id WHERE d.user_sender_id = ? AND d.user_receiver_id = ? " +

            "ORDER BY date ASC " +
            "LIMIT ?, ?";
    
    /** The Constant ADD_MESSAGE_SQL. */
    private static final String ADD_MESSAGE_SQL = "INSERT INTO dialoges values(null, ?, ?, ?, ?)";

    /**
     * Search all messages from to.
     *
     * @param fromUserId the from user id
     * @param toUserId the to user id
     * @return the list
     */
    @Override
    public List<UserMessage> searchAllMessagesFromTo(long fromUserId, long toUserId) {
        List<UserMessage> messages;

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try(Connection connection = ConnectionPool.getInstance().getConnection()){

            preparedStatement = connection.prepareStatement(SEARCH_MESSAGE_FROM_TO_SQL);
            preparedStatement.setLong(1, fromUserId);
            preparedStatement.setLong(2, toUserId);
            resultSet = preparedStatement.executeQuery();

            messages = DialogMapper.createMessages(resultSet);

        }
        catch (SQLException e) {
            LOGGER.warn("messages from to are not founded", e);
            return new ArrayList<>();
        }
        finally {
            try {
                DbUtils.close(resultSet);
            }
            catch (SQLException e) {
                LOGGER.error(PufarDaoConstant.CLOSE_RESULTSET_ERROR_LOG);
            }
            try {
                DbUtils.close(preparedStatement);
            }
            catch (SQLException e) {
                LOGGER.error(PufarDaoConstant.CLOSE_STATEMENT_ERROR_LOG);
            }
        }

        return messages;
    }
    
    /**
     * Search all messages between.
     *
     * @param userId1 the user id 1
     * @param userId2 the user id 2
     * @return the list
     */
    @Override
    public List<UserMessage> searchAllMessagesBetween(long userId1, long userId2) {
        List<UserMessage> messages;

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try(Connection connection = ConnectionPool.getInstance().getConnection()){
            preparedStatement = connection.prepareStatement(SEARCH_MESSAGE_BETWEEN_SQL);
            preparedStatement.setLong(1, userId1);
            preparedStatement.setLong(2, userId2);
            preparedStatement.setLong(3, userId2);
            preparedStatement.setLong(4, userId1);
            resultSet = preparedStatement.executeQuery();

            messages = DialogMapper.createMessages(resultSet);

        }
        catch (SQLException e) {
            LOGGER.warn("messages between are not founded", e);
            return new ArrayList<>();
        }
        finally {
            try {
                DbUtils.close(resultSet);
            }
            catch (SQLException e) {
                LOGGER.error(PufarDaoConstant.CLOSE_RESULTSET_ERROR_LOG);
            }
            try {
                DbUtils.close(preparedStatement);
            }
            catch (SQLException e) {
                LOGGER.error(PufarDaoConstant.CLOSE_STATEMENT_ERROR_LOG);
            }
        }

        return messages;
    }
    
    /**
     * Search messages between with limit.
     *
     * @param userId1 the user id 1
     * @param userId2 the user id 2
     * @param since the since
     * @param howMuch the how much
     * @return the list
     */
    @Override
    public List<UserMessage> searchMessagesBetweenWithLimit(long userId1, long userId2, int since, int howMuch) {

        List<UserMessage> messages;

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try(Connection connection = ConnectionPool.getInstance().getConnection()){
            preparedStatement = connection.prepareStatement(SEARCH_MESSAGE_BETWEEN_WITH_LIMIT_SQL);
            preparedStatement.setLong(1, userId1);
            preparedStatement.setLong(2, userId2);
            preparedStatement.setLong(3, userId2);
            preparedStatement.setLong(4, userId1);
            preparedStatement.setInt(5, since);
            preparedStatement.setInt(6, howMuch);
            resultSet = preparedStatement.executeQuery();

            messages = DialogMapper.createMessages(resultSet);

        }
        catch (SQLException e) {
            LOGGER.warn("messages between with limit are not founded", e);
            return new ArrayList<>();
        }
        finally {
            try {
                DbUtils.close(resultSet);
            }
            catch (SQLException e) {
                LOGGER.error(PufarDaoConstant.CLOSE_RESULTSET_ERROR_LOG);
            }
            try {
                DbUtils.close(preparedStatement);
            }
            catch (SQLException e) {
                LOGGER.error(PufarDaoConstant.CLOSE_STATEMENT_ERROR_LOG);
            }
        }

        return messages;
    }
    
    /**
     * Search popular user.
     *
     * @param forWhomUserId the for whom user id
     * @param howMuch the how much
     * @return the list
     */
    @Override
    public List<User> searchPopularUser(long forWhomUserId, int howMuch) {
        List<User> users;

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try(Connection connection = ConnectionPool.getInstance().getConnection()){
            preparedStatement = connection.prepareStatement(SEARCH_POPULAR_USERS_SQL);

            preparedStatement.setLong(1, forWhomUserId);
            preparedStatement.setLong(2, forWhomUserId);
            preparedStatement.setLong(3, forWhomUserId);
            preparedStatement.setLong(4, forWhomUserId);
            preparedStatement.setLong(5, forWhomUserId);
            preparedStatement.setLong(6, forWhomUserId);

            preparedStatement.setInt(7, howMuch);
            resultSet = preparedStatement.executeQuery();

            users = UserMapper.createUsers(resultSet);

        }
        catch (SQLException | PufarDAOException e) {
            LOGGER.warn("popular users are not founded", e);
            return new ArrayList<>();
        }
        finally {
            try { DbUtils.close(resultSet); } catch (SQLException e) { LOGGER.error(PufarDaoConstant.CLOSE_RESULTSET_ERROR_LOG); }
            try { DbUtils.close(preparedStatement); } catch (SQLException e) { LOGGER.error(PufarDaoConstant.CLOSE_STATEMENT_ERROR_LOG); }
        }

        return users;
    }

    /**
     * Adds the message.
     *
     * @param senderId the sender id
     * @param receiverId the receiver id
     * @param message the message
     * @throws PufarDAOException the pufar DAO exception
     */
    @Override
    public void addMessage(long senderId, long receiverId, String message) throws PufarDAOException{
        PreparedStatement insertStatement = null;
        try(Connection connection = ConnectionPool.getInstance().getConnection()) {
            insertStatement = connection.prepareStatement(ADD_MESSAGE_SQL);

            insertStatement.setLong(1, senderId);
            insertStatement.setLong(2, receiverId);
            insertStatement.setString(3, message);
            long currentTime = System.currentTimeMillis();
            insertStatement.setLong(4, currentTime);

            insertStatement.executeUpdate();

        }
        catch (SQLException e){
            throw new PufarDAOException(e);
        }
        finally {
            try { DbUtils.close(insertStatement); }
            catch (SQLException e) { LOGGER.error(PufarDaoConstant.CLOSE_STATEMENT_ERROR_LOG); }
        }

    }

}
