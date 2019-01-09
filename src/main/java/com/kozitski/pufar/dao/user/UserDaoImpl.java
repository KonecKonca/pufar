package com.kozitski.pufar.dao.user;

import com.kozitski.pufar.database.ConnectionPool;
import com.kozitski.pufar.dao.PufarDaoConstant;
import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.entity.user.UserParameter;
import com.kozitski.pufar.entity.user.UserStatus;
import com.kozitski.pufar.entity.user.Users;
import com.kozitski.pufar.exception.PufarDAOException;
import com.kozitski.pufar.util.mapper.user.UserMapper;
import org.apache.commons.dbutils.DbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// TODO: Auto-generated Javadoc
/**
 * The Class UserDaoImpl.
 */
public class UserDaoImpl implements UserDao {
    
    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);

    /** The Constant USER_ORDINAL_STATUS_INCREMENT. */
    private static final int USER_ORDINAL_STATUS_INCREMENT = 1;

    /** The Constant SEARCH_USER_BY_ID. */
    private static final String SEARCH_USER_BY_ID = "SELECT u.user_id, u.login, u.password, s.value status, ban_status isBanned FROM users u LEFT JOIN statuses s ON u.status = s.status_id WHERE user_id = ?";
    
    /** The Constant SEARCH_USER_BY_LOGIN. */
    private static final String SEARCH_USER_BY_LOGIN = "SELECT u.user_id , u.login, u.password, s.value status, ban_status isBanned FROM users u LEFT JOIN statuses s ON u.status = s.status_id WHERE u.login = ?";
    
    /** The Constant SEARCH_USER_BY_STATUS. */
    private static final String SEARCH_USER_BY_STATUS = "SELECT u.user_id, u.login, u.password, s.value status, ban_status isBanned FROM users u LEFT JOIN statuses s ON u.status = s.status_id WHERE s.value = ?";
    
    /** The Constant INSERT_NEW_USER_COMMON. */
    private static final String INSERT_NEW_USER_COMMON = "INSERT INTO users values(null, ?, ?, ?, 0, null)";

    /** The Constant SEARCH_USER_WITH_PARAMETERS_SQL_START. */
    // search with parameters
    private static final String SEARCH_USER_WITH_PARAMETERS_SQL_START = "SELECT u.user_id, u.login, u.password, s.value status, ban_status isBanned FROM users u LEFT JOIN statuses s ON u.status = s.status_id ";
    
    /** The Constant SEARCH_USER_WITH_PARAMETERS_SQL_WHERE. */
    private static final String SEARCH_USER_WITH_PARAMETERS_SQL_WHERE = "WHERE ";
    
    /** The Constant AND. */
    private static final String AND = " AND ";
    
    /** The Constant SEARCH_USER_WITH_PARAMETERS_SQL_LIMIT. */
    private static final String SEARCH_USER_WITH_PARAMETERS_SQL_LIMIT = "LIMIT 200";

    /** The Constant SEARCH_USER_WITH_PARAMETERS_SQL_WHERE_ID. */
    private static final String SEARCH_USER_WITH_PARAMETERS_SQL_WHERE_ID = "u.user_id=?";
    
    /** The Constant SEARCH_USER_WITH_PARAMETERS_SQL_WHERE_LOGIN. */
    private static final String SEARCH_USER_WITH_PARAMETERS_SQL_WHERE_LOGIN = "u.login LIKE ?";
    
    /** The Constant SEARCH_USER_WITH_PARAMETERS_SQL_WHERE_STATUS. */
    private static final String SEARCH_USER_WITH_PARAMETERS_SQL_WHERE_STATUS = "s.value=?";

    /** The Constant BAN_USER_SQL. */
    private static final String BAN_USER_SQL = "UPDATE users SET ban_status=? WHERE user_id=?";
    
    /** The Constant CHANGE_USER_LOGIN_SQL. */
    private static final String CHANGE_USER_LOGIN_SQL = "UPDATE users SET login=? WHERE user_id=?";
    
    /** The Constant CHANGE_USER_STATUS_SQL. */
    private static final String CHANGE_USER_STATUS_SQL = "UPDATE users SET status=? WHERE user_id=?";

    /** The Constant CHANGE_PASSWORD. */
    private static final String CHANGE_PASSWORD = "UPDATE users SET password=? WHERE user_id=?";

    /**
     * Search by id.
     *
     * @param id the id
     * @return the optional
     */
    @Override
    public Optional<User> searchById(long id) {
        Optional<User> user;

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try(Connection  connection = ConnectionPool.getInstance().getConnection()){
            preparedStatement = connection.prepareStatement(SEARCH_USER_BY_ID);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                User findUser = UserMapper.createUser(resultSet);
                user = Optional.of(findUser);
            }
            else {
                user = Optional.empty();
            }

        }
        catch (SQLException | PufarDAOException e) {
            return Optional.empty();
        }
        finally {
            try { DbUtils.close(resultSet); } catch (SQLException e) { LOGGER.error(PufarDaoConstant.CLOSE_RESULTSET_ERROR_LOG); }
            try { DbUtils.close(preparedStatement); } catch (SQLException e) { LOGGER.error(PufarDaoConstant.CLOSE_STATEMENT_ERROR_LOG); }
        }

        return user;
    }
    
    /**
     * Search user by login.
     *
     * @param login the login
     * @return the optional
     */
    @Override
    public Optional<User> searchUserByLogin(String login) {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try(Connection  connection = ConnectionPool.getInstance().getConnection()){
            preparedStatement = connection.prepareStatement(SEARCH_USER_BY_LOGIN);
            preparedStatement.setString(1, login);
            resultSet = preparedStatement.executeQuery();

            User user = null;
            if(resultSet.next()){
                user = UserMapper.createUser(resultSet);
            }

            return Optional.ofNullable(user);
        }
        catch (SQLException | PufarDAOException e) {
            return Optional.empty();
        }
        finally {
            try { DbUtils.close(resultSet); } catch (SQLException e) { LOGGER.error(PufarDaoConstant.CLOSE_RESULTSET_ERROR_LOG); }
            try { DbUtils.close(preparedStatement); } catch (SQLException e) { LOGGER.error(PufarDaoConstant.CLOSE_STATEMENT_ERROR_LOG); }
        }

    }
    
    /**
     * Search users by status.
     *
     * @param status the status
     * @return the list
     */
    @Override
    public List<User> searchUsersByStatus(UserStatus status) {

        List<User> result;

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try(Connection  connection = ConnectionPool.getInstance().getConnection()){
            preparedStatement = connection.prepareStatement(SEARCH_USER_BY_STATUS);
            preparedStatement.setString(1, status.name());
            resultSet = preparedStatement.executeQuery();

            result = UserMapper.createUsers(resultSet);

        }
        catch (SQLException | PufarDAOException e) {
            return new  ArrayList<>();
        }
        finally {
            try { DbUtils.close(resultSet); } catch (SQLException e) { LOGGER.error(PufarDaoConstant.CLOSE_RESULTSET_ERROR_LOG); }
            try { DbUtils.close(preparedStatement); } catch (SQLException e) { LOGGER.error(PufarDaoConstant.CLOSE_STATEMENT_ERROR_LOG); }
        }

        return result;
    }
    
    /**
     * Adds the user.
     *
     * @param user the user
     * @return the user
     * @throws PufarDAOException the pufar DAO exception
     */
    @Override
    public User addUser(User user) throws PufarDAOException {

        PreparedStatement searchUserStatement = null;
        PreparedStatement userAddStatement = null;

        ResultSet searchUserResultSet = null;
        ResultSet generatedKeys = null;

        Connection connection = null;
        try{
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);

            searchUserStatement = connection.prepareStatement(SEARCH_USER_BY_LOGIN);
            searchUserStatement.setString(1, user.getLogin());
            searchUserResultSet = searchUserStatement.executeQuery();

            if(searchUserResultSet.next()){
                throw new PufarDAOException("User with input login already exist");
            }
            else {
                userAddStatement = connection.prepareStatement(INSERT_NEW_USER_COMMON, Statement.RETURN_GENERATED_KEYS);
                userAddStatement.setString(1, user.getLogin());
                userAddStatement.setString(2, user.getPassword());
                userAddStatement.setInt(3, user.getStatus().ordinal() + USER_ORDINAL_STATUS_INCREMENT);

                userAddStatement.executeUpdate();

                // search last Auto_increment value
                generatedKeys = userAddStatement.getGeneratedKeys();
                generatedKeys.next();
                long lastUserId =  generatedKeys.getLong(1);
                user.setUserId(lastUserId);

                connection.commit();
                LOGGER.info("User correctly added");

                return user;
            }
        }
        catch (SQLException e) {
            try { connection.rollback(); } catch (SQLException e1) { LOGGER.error(PufarDaoConstant.ROLLBACK_LOG); }
            throw new PufarDAOException("User not added", e);
        }
        finally {
            try { DbUtils.close(searchUserResultSet); } catch (SQLException e) { LOGGER.error(PufarDaoConstant.CLOSE_RESULTSET_ERROR_LOG); }
            try { DbUtils.close(generatedKeys); } catch (SQLException e) { LOGGER.error(PufarDaoConstant.CLOSE_RESULTSET_ERROR_LOG); }

            try { DbUtils.close(searchUserStatement); } catch (SQLException e) { LOGGER.error(PufarDaoConstant.CLOSE_STATEMENT_ERROR_LOG); }
            try { DbUtils.close(userAddStatement); } catch (SQLException e) { LOGGER.error(PufarDaoConstant.CLOSE_STATEMENT_ERROR_LOG); }

            try { DbUtils.close(connection); } catch (SQLException e) { LOGGER.error(PufarDaoConstant.CLOSE_CONNECTION_LOG); }
        }

    }
    
    /**
     * Search by parameters.
     *
     * @param parameters the parameters
     * @return the list
     */
    @Override
    public List<User> searchByParameters(UserParameter parameters) {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try(Connection connection = ConnectionPool.getInstance().getConnection()){
            String parametersSql = generateSearchWithParametersSql(parameters);

            preparedStatement = connection.prepareStatement(parametersSql);
            fullFillPreparedStatement(preparedStatement, parameters);

            resultSet = preparedStatement.executeQuery();

            return UserMapper.createUsers(resultSet);
        }
        catch (SQLException | PufarDAOException e) {
            return new ArrayList<>();
        }
        finally {
            try { DbUtils.close(resultSet); } catch (SQLException e) { LOGGER.error(PufarDaoConstant.CLOSE_RESULTSET_ERROR_LOG); }
            try { DbUtils.close(preparedStatement); } catch (SQLException e) { LOGGER.error(PufarDaoConstant.CLOSE_STATEMENT_ERROR_LOG); }
        }

    }
    
    /**
     * Generate search with parameters sql.
     *
     * @param parameters the parameters
     * @return the string
     */
    private String generateSearchWithParametersSql(UserParameter parameters){
        StringBuilder addSql = new StringBuilder();
        addSql.append(SEARCH_USER_WITH_PARAMETERS_SQL_START);

        ArrayList<String> whereConditions = new ArrayList<>();
        if(parameters.getUserId() != null){
            whereConditions.add(SEARCH_USER_WITH_PARAMETERS_SQL_WHERE_ID);
        }
        if(parameters.getLoginStart() != null){
            whereConditions.add(SEARCH_USER_WITH_PARAMETERS_SQL_WHERE_LOGIN);
        }
        if(parameters.getStatus() != null){
            whereConditions.add(SEARCH_USER_WITH_PARAMETERS_SQL_WHERE_STATUS);
        }

        if(!whereConditions.isEmpty()){
            addSql.append(SEARCH_USER_WITH_PARAMETERS_SQL_WHERE);
            for (int i = 0; i < whereConditions.size() - 1; i++) {
                addSql.append(whereConditions.get(i));
                addSql.append(AND);
            }
            addSql.append(whereConditions.get(whereConditions.size() - 1));

        }
        addSql.append(" ");
        addSql.append(SEARCH_USER_WITH_PARAMETERS_SQL_LIMIT);

        return addSql.toString();
    }
    
    /**
     * Full fill prepared statement.
     *
     * @param preparedStatement the prepared statement
     * @param parameters the parameters
     * @throws SQLException the SQL exception
     */
    private void fullFillPreparedStatement(PreparedStatement preparedStatement, UserParameter parameters) throws SQLException {
        int counter = 1;

        if(parameters.getUserId() != null){
            preparedStatement.setLong(counter++, parameters.getUserId());
        }
        if(parameters.getLoginStart() != null){
            preparedStatement.setString(counter++, parameters.getLoginStart() + "%");
        }
        if(parameters.getStatus() != null){
            preparedStatement.setString(counter, parameters.getStatus().name().toUpperCase());
        }
    }

    /**
     * Insert ban status.
     *
     * @param userId the user id
     * @param currentUser the current user
     * @param banStatus the ban status
     * @return true, if successful
     */
    @Override
    public boolean insertBanStatus(long userId, User currentUser, int banStatus) {

        PreparedStatement searchUserStatement = null;
        PreparedStatement banStatement = null;

        ResultSet resultSet = null;

        Connection connection = null;
        try{
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);

            searchUserStatement = connection.prepareStatement(SEARCH_USER_BY_ID);
            searchUserStatement.setLong(1, userId);
            resultSet = searchUserStatement.executeQuery();

            User user;
            if(resultSet.next()){
                 user = UserMapper.createUser(resultSet);

                if(Users.checkAccessRight(currentUser, user)){
                    banStatement = connection.prepareStatement(BAN_USER_SQL);
                    banStatement.setInt(1, banStatus);
                    banStatement.setLong(2, userId);
                    banStatement.executeUpdate();

                    connection.commit();
                    return true;
                }
                else{
                    return false;
                }
            }

            return false;
        }
        catch (SQLException | PufarDAOException e) {
            try { connection.rollback(); } catch (SQLException e1) { LOGGER.error(PufarDaoConstant.ROLLBACK_LOG); }
            return false;
        }
        finally {
            try { DbUtils.close(resultSet); } catch (SQLException e) { LOGGER.error(PufarDaoConstant.CLOSE_RESULTSET_ERROR_LOG); }

            try { DbUtils.close(searchUserStatement); } catch (SQLException e) { LOGGER.error(PufarDaoConstant.CLOSE_STATEMENT_ERROR_LOG); }
            try { DbUtils.close(banStatement); } catch (SQLException e) { LOGGER.error(PufarDaoConstant.CLOSE_STATEMENT_ERROR_LOG); }

            try { DbUtils.close(connection); } catch (SQLException e) { LOGGER.error(PufarDaoConstant.CLOSE_CONNECTION_LOG); }
        }

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

        PreparedStatement searchUserStatement = null;
        PreparedStatement searchByNewLogin = null;
        PreparedStatement changeLoginStatement = null;

        ResultSet searchUserResultSet = null;
        ResultSet checkNewLoginResultSet = null;

        Connection connection = null;
        try{
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);

            searchUserStatement = connection.prepareStatement(SEARCH_USER_BY_ID);
            searchUserStatement.setLong(1, id);
            searchUserResultSet = searchUserStatement.executeQuery();

            User user;
            if(searchUserResultSet.next()){
                user = UserMapper.createUser(searchUserResultSet);

                if(Users.checkAccessRight(currentUser, user)){

                    searchByNewLogin = connection.prepareStatement(SEARCH_USER_BY_LOGIN);
                    searchByNewLogin.setString(1, newLogin);
                    checkNewLoginResultSet = searchByNewLogin.executeQuery();

                    if(!checkNewLoginResultSet.next()){

                        changeLoginStatement = connection.prepareStatement(CHANGE_USER_LOGIN_SQL);
                        changeLoginStatement.setString(1, newLogin);
                        changeLoginStatement.setLong(2, id);
                        changeLoginStatement.executeUpdate();

                        connection.commit();
                        return true;
                    }

                }
                else{
                    return false;
                }
            }

            return false;
        }
        catch (SQLException | PufarDAOException e) {
            try { connection.rollback(); } catch (SQLException e1) { LOGGER.error(PufarDaoConstant.ROLLBACK_LOG); }
            return false;
        }
        finally {
            try { DbUtils.close(searchUserResultSet); } catch (SQLException e) { LOGGER.error(PufarDaoConstant.CLOSE_RESULTSET_ERROR_LOG); }
            try { DbUtils.close(checkNewLoginResultSet); } catch (SQLException e) { LOGGER.error(PufarDaoConstant.CLOSE_RESULTSET_ERROR_LOG); }

            try { DbUtils.close(searchUserStatement); } catch (SQLException e) { LOGGER.error(PufarDaoConstant.CLOSE_STATEMENT_ERROR_LOG); }
            try { DbUtils.close(searchByNewLogin); } catch (SQLException e) { LOGGER.error(PufarDaoConstant.CLOSE_STATEMENT_ERROR_LOG); }
            try { DbUtils.close(changeLoginStatement); } catch (SQLException e) { LOGGER.error(PufarDaoConstant.CLOSE_STATEMENT_ERROR_LOG); }

            try { DbUtils.close(connection); } catch (SQLException e) { LOGGER.error(PufarDaoConstant.CLOSE_CONNECTION_LOG); }
        }

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
    public boolean changeUserStatusByUserId(long id, UserStatus newStatus, User currentUser) {

        PreparedStatement searchUserStatement = null;
        PreparedStatement changeUserStatus = null;

        ResultSet resultSet = null;

        Connection connection = null;
        try{
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);

            searchUserStatement = connection.prepareStatement(SEARCH_USER_BY_ID);
            searchUserStatement.setLong(1, id);
            resultSet = searchUserStatement.executeQuery();

            User user;
            if(resultSet.next()){
                user = UserMapper.createUser(resultSet);

                if(Users.checkAccessRight(currentUser, user)){

                    changeUserStatus = connection.prepareStatement(CHANGE_USER_STATUS_SQL);
                    int status = Users.statusPriority(newStatus);
                    changeUserStatus.setInt(1, status);
                    changeUserStatus.setLong(2, id);

                    changeUserStatus.executeUpdate();

                    connection.commit();
                    return true;
                }
                else{
                    return false;
                }
            }

            return false;
        }
        catch (SQLException | PufarDAOException e) {
            try { connection.rollback(); } catch (SQLException e1) { LOGGER.error(PufarDaoConstant.ROLLBACK_LOG); }
            return false;
        }
        finally {
            try { DbUtils.close(resultSet); } catch (SQLException e) { LOGGER.error(PufarDaoConstant.CLOSE_RESULTSET_ERROR_LOG); }

            try { DbUtils.close(searchUserStatement); } catch (SQLException e) { LOGGER.error(PufarDaoConstant.CLOSE_STATEMENT_ERROR_LOG); }
            try { DbUtils.close(changeUserStatus); } catch (SQLException e) { LOGGER.error(PufarDaoConstant.CLOSE_STATEMENT_ERROR_LOG); }
            try { DbUtils.close(connection); } catch (SQLException e) { LOGGER.error(PufarDaoConstant.CLOSE_CONNECTION_LOG); }

        }

    }

    /**
     * Change password.
     *
     * @param userId the user id
     * @param newPassword the new password
     * @throws PufarDAOException the pufar DAO exception
     */
    @Override
    public void changePassword(long userId, String newPassword) throws PufarDAOException {

        PreparedStatement preparedStatement = null;

        try(Connection connection = ConnectionPool.getInstance().getConnection()){
            preparedStatement = connection.prepareStatement(CHANGE_PASSWORD);
            preparedStatement.setString(1, newPassword);
            preparedStatement.setLong(2, userId);

            preparedStatement.executeUpdate();

        }
        catch (SQLException  e) {
            throw new PufarDAOException(e);
        }
        finally {
            try { DbUtils.close(preparedStatement); } catch (SQLException e) { LOGGER.error(PufarDaoConstant.CLOSE_STATEMENT_ERROR_LOG); }
        }

    }

}
