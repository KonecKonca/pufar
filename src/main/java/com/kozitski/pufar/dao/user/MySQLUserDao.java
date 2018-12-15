package com.kozitski.pufar.dao.user;

import com.kozitski.pufar.connection.PoolConnection;
import com.kozitski.pufar.entity.notification.NotificationParameter;
import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.entity.user.UserParameter;
import com.kozitski.pufar.entity.user.UserStatus;
import com.kozitski.pufar.exception.PufarDAOException;
import com.kozitski.pufar.util.mapper.notification.NotificationMapper;
import com.kozitski.pufar.util.mapper.user.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MySQLUserDao implements UserDao {
    private static Logger LOGGER = LoggerFactory.getLogger(MySQLUserDao.class);

    private static final int USER_ORDINAL_STATUS_INCREMENT = 1;

    private static final String SEARCH_USER_BY_ID = "SELECT u.user_id, u.login, u.password, s.value status, ban_status isBanned FROM users u LEFT JOIN statuses s ON u.status = s.status_id WHERE user_id = ?";
    private static final String SEARCH_USER_BY_LOGIN = "SELECT u.user_id , u.login, u.password, s.value status, ban_status isBanned FROM users u LEFT JOIN statuses s ON u.status = s.status_id WHERE u.login = ?";
    private static final String SEARCH_USER_BY_STATUS = "SELECT u.user_id, u.login, u.password, s.value status, ban_status isBanned FROM users u LEFT JOIN statuses s ON u.status = s.status_id WHERE s.value = ?";
    private static final String INSERT_NEW_USER_COMMON = "INSERT INTO users values(null, ?, ?, ?, 0)";

    // search with parameters
    private static final String SEARCH_USER_WITH_PARAMETERS_SQL_START = "SELECT u.user_id, u.login, u.password, s.value status, ban_status isBanned FROM users u LEFT JOIN statuses s ON u.status = s.status_id ";
    private static final String SEARCH_USER_WITH_PARAMETERS_SQL_WHERE = "WHERE ";
    private static final String AND = " AND ";
    private static final String SEARCH_USER_WITH_PARAMETERS_SQL_LIMIT = "LIMIT 200";

    private static final String SEARCH_USER_WITH_PARAMETERS_SQL_WHERE_ID = "u.user_id=?";
    private static final String SEARCH_USER_WITH_PARAMETERS_SQL_WHERE_LOGIN = "u.login LIKE ?";
    private static final String SEARCH_USER_WITH_PARAMETERS_SQL_WHERE_STATUS = "s.value=?";

    private static final String BAN_USER_SQL = "UPDATE users SET ban_status=1 WHERE user_id=?";

    //  need in debug
    @Override
    public Optional<User> searchById(long id) {

        try(Connection  connection = PoolConnection.getInstance().getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_USER_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            User user = UserMapper.createUser(resultSet);

            return Optional.of(user);
        }

        catch (SQLException | PufarDAOException e) {
            return Optional.empty();
        }

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
                PreparedStatement userAddStatement = connection.prepareStatement(INSERT_NEW_USER_COMMON);
                userAddStatement.setString(1, user.getLogin());
                userAddStatement.setString(2, user.getPassword());
                userAddStatement.setInt(3, user.getStatus().ordinal() + USER_ORDINAL_STATUS_INCREMENT);

                userAddStatement.executeUpdate();
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
    public boolean insertBanStatus(long userId, User currentUser) {

        try(Connection  connection = PoolConnection.getInstance().getConnection()){
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_USER_BY_ID);
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            User user;
            if(resultSet.next()){
                 user = UserMapper.createUser(resultSet);

                if((user.getStatus().equals(UserStatus.SIMPLE_USER) && currentUser.getStatus().equals(UserStatus.ADMIN))
                        || (currentUser.getStatus().equals(UserStatus.SUPER_ADMIN)) && (user.getStatus().equals(UserStatus.SIMPLE_USER) || user.getStatus().equals(UserStatus.ADMIN))){
                    PreparedStatement dropStatement = connection.prepareStatement(BAN_USER_SQL);
                    dropStatement.setLong(1, userId);
                    dropStatement.executeUpdate();

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


}
