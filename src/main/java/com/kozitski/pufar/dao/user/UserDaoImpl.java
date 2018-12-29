package com.kozitski.pufar.dao.user;

import com.kozitski.pufar.connection.PoolConnection;
import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.entity.user.UserParameter;
import com.kozitski.pufar.entity.user.UserStatus;
import com.kozitski.pufar.entity.user.Users;
import com.kozitski.pufar.exception.PufarDAOException;
import com.kozitski.pufar.util.mapper.user.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class UserDaoImpl implements UserDao {
    private static Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);

    private static final int USER_ORDINAL_STATUS_INCREMENT = 1;

    private static final String SEARCH_USER_BY_ID = "SELECT u.user_id, u.login, u.password, s.value status, ban_status isBanned FROM users u LEFT JOIN statuses s ON u.status = s.status_id WHERE user_id = ?";
    private static final String SEARCH_USER_BY_LOGIN = "SELECT u.user_id , u.login, u.password, s.value status, ban_status isBanned FROM users u LEFT JOIN statuses s ON u.status = s.status_id WHERE u.login = ?";
    private static final String SEARCH_USER_BY_STATUS = "SELECT u.user_id, u.login, u.password, s.value status, ban_status isBanned FROM users u LEFT JOIN statuses s ON u.status = s.status_id WHERE s.value = ?";
    private static final String INSERT_NEW_USER_COMMON = "INSERT INTO users values(null, ?, ?, ?, 0, null)";

    // search with parameters
    private static final String SEARCH_USER_WITH_PARAMETERS_SQL_START = "SELECT u.user_id, u.login, u.password, s.value status, ban_status isBanned FROM users u LEFT JOIN statuses s ON u.status = s.status_id ";
    private static final String SEARCH_USER_WITH_PARAMETERS_SQL_WHERE = "WHERE ";
    private static final String AND = " AND ";
    private static final String SEARCH_USER_WITH_PARAMETERS_SQL_LIMIT = "LIMIT 200";

    private static final String SEARCH_USER_WITH_PARAMETERS_SQL_WHERE_ID = "u.user_id=?";
    private static final String SEARCH_USER_WITH_PARAMETERS_SQL_WHERE_LOGIN = "u.login LIKE ?";
    private static final String SEARCH_USER_WITH_PARAMETERS_SQL_WHERE_STATUS = "s.value=?";

    private static final String BAN_USER_SQL = "UPDATE users SET ban_status=? WHERE user_id=?";
    private static final String CHANGE_USER_LOGIN_SQL = "UPDATE users SET login=? WHERE user_id=?";
    private static final String CHANGE_USER_STATUS_SQL = "UPDATE users SET status=? WHERE user_id=?";

    private static final String CHANGE_PASSWORD = "UPDATE users SET password=? WHERE user_id=?";

    @Override
    public Optional<User> searchById(long id) {
        Optional<User> user;

        try(Connection  connection = PoolConnection.getInstance().getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_USER_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

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

        return user;
    }
    @Override
    public Optional<User> searchUserByLogin(String login) {

        try(Connection  connection = PoolConnection.getInstance().getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_USER_BY_LOGIN);
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();

            User user = null;
            if(resultSet.next()){
                user = UserMapper.createUser(resultSet);
            }

            return Optional.ofNullable(user);
        }

        catch (SQLException | PufarDAOException e) {
            return Optional.empty();
        }

    }
    @Override
    public ArrayList<User> searchUsersByStatus(UserStatus status) {

        ArrayList<User> result;

        try(Connection  connection = PoolConnection.getInstance().getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_USER_BY_STATUS);
            preparedStatement.setString(1, status.name());
            ResultSet resultSet = preparedStatement.executeQuery();

            result = UserMapper.createUsers(resultSet);

        }
        catch (SQLException | PufarDAOException e) {
            return new  ArrayList<>();
        }

        return result;
    }
    @Override
    public User addUser(User user) throws PufarDAOException {

        try(Connection  connection = PoolConnection.getInstance().getConnection()){
            // my be not so hard LEVEL
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_USER_BY_LOGIN);
            preparedStatement.setString(1, user.getLogin());
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                throw new PufarDAOException("User with input login already exist");
            }
            else {
                PreparedStatement userAddStatement = connection.prepareStatement(INSERT_NEW_USER_COMMON, Statement.RETURN_GENERATED_KEYS);
                userAddStatement.setString(1, user.getLogin());
                userAddStatement.setString(2, user.getPassword());
                userAddStatement.setInt(3, user.getStatus().ordinal() + USER_ORDINAL_STATUS_INCREMENT);

                userAddStatement.executeUpdate();

                // search last Auto_increment value
                ResultSet generatedKeys = userAddStatement.getGeneratedKeys();
                generatedKeys.next();
                long lastUserId =  generatedKeys.getLong(1);
                user.setUserId(lastUserId);

                connection.commit();
                LOGGER.info("User correctly added");

                return user;
            }
        }
        catch (SQLException e) {
            throw new PufarDAOException("User not added", e);
        }

    }
    @Override
    public ArrayList<User> searchByParameters(UserParameter parameters) {

        try(Connection connection = PoolConnection.getInstance().getConnection()){
            String parametersSql = generateSearchWithParametersSql(parameters);

            PreparedStatement preparedStatement = connection.prepareStatement(parametersSql);
            fullFillPreparedStatement(preparedStatement, parameters);

            ResultSet resultSet = preparedStatement.executeQuery();

            return UserMapper.createUsers(resultSet);
        }

        catch (SQLException | PufarDAOException e) {
            return new ArrayList<>();
        }

    }
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

        if(whereConditions.size() != 0){
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

    @Override
    public boolean insertBanStatus(long userId, User currentUser, int banStatus) {

        try(Connection  connection = PoolConnection.getInstance().getConnection()){
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_USER_BY_ID);
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            User user;
            if(resultSet.next()){
                 user = UserMapper.createUser(resultSet);

                if(Users.checkAccessRight(currentUser, user)){
                    PreparedStatement banStatement = connection.prepareStatement(BAN_USER_SQL);
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
            return false;
        }

    }
    @Override
    public boolean changeUserLogin(long id, String newLogin, User currentUser) {

        try(Connection  connection = PoolConnection.getInstance().getConnection()){
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_USER_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            User user;
            if(resultSet.next()){
                user = UserMapper.createUser(resultSet);

                if(Users.checkAccessRight(currentUser, user)){

                    PreparedStatement searchByNewLogin = connection.prepareStatement(SEARCH_USER_BY_LOGIN);
                    searchByNewLogin.setString(1, newLogin);
                    ResultSet checkNewLoginResultSet = searchByNewLogin.executeQuery();

                    if(!checkNewLoginResultSet.next()){

                        PreparedStatement changeLoginStatement = connection.prepareStatement(CHANGE_USER_LOGIN_SQL);
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
            return false;
        }

    }
    @Override
    public boolean changeUserStatusByUserId(long id, UserStatus newStatus, User currentUser) {
        try(Connection  connection = PoolConnection.getInstance().getConnection()){
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_USER_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            User user;
            if(resultSet.next()){
                user = UserMapper.createUser(resultSet);

                if(Users.checkAccessRight(currentUser, user)){

                    PreparedStatement changeUserStatus = connection.prepareStatement(CHANGE_USER_STATUS_SQL);
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
            return false;
        }
    }

    @Override
    public void changePassword(long userId, String newPassword) throws PufarDAOException {

        try(Connection connection = PoolConnection.getInstance().getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(CHANGE_PASSWORD);
            preparedStatement.setString(1, newPassword);
            preparedStatement.setLong(2, userId);

            preparedStatement.executeUpdate();

        }

        catch (SQLException  e) {
            throw new PufarDAOException(e);
        }

    }

}
