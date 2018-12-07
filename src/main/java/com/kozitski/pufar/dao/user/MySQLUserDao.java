package com.kozitski.pufar.dao.user;

import com.kozitski.pufar.connection.PoolConnection;
import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.entity.user.UserStatus;
import com.kozitski.pufar.exception.PufarDAOException;
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

    private static final String SEARCH_USER_BY_ID = "SELECT u.user_id, u.login, u.password, s.value FROM users u LEFT JOIN statuses s ON u.status = s.status_id WHERE user_id = ?";
    private static final String SEARCH_USER_BY_LOGIN = "SELECT u.user_id , u.login, u.password, s.value FROM users u LEFT JOIN statuses s ON u.status = s.status_id WHERE u.login = ?";
    private static final String SEARCH_USER_BY_STATUS = "SELECT u.user_id, u.login, u.password, s.value FROM users u LEFT JOIN statuses s ON u.status = s.status_id WHERE s.value = ?";
    private static final String INSERT_NEW_USER_COMMON = "INSERT INTO users values(null, ?, ?, ?)";

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

        catch (SQLException e) {
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

        catch (SQLException e) {
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
        catch (SQLException e) {
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

}
