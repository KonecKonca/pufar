package com.kozitski.pufar.dao.number;

import com.kozitski.pufar.connection.PoolConnection;
import com.kozitski.pufar.entity.number.MobilPhoneNumber;
import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.exception.PufarDAOException;
import com.kozitski.pufar.util.mapper.comment.CommentMapper;
import com.kozitski.pufar.util.mapper.number.MobilNumberMapper;
import com.kozitski.pufar.util.mapper.user.UserMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class NumberDaoImpl implements NumberDao {

    private static final String SEARCH_NUMBER_BY_USER_ID =
            "SELECT n.number_id, n.country, n.operator, n.number FROM numbers n " +
                "INNER JOIN users u ON u.number_id=n.number_id " +
            "WHERE u.user_id=?";

    private static final String UPDATE_NUMBER_BY_USER_ID =
            "UPDATE numbers n " +
                "LEFT JOIN users u ON n.number_id=u.number_id " +
            "SET country=?, operator=?, number=?" +
                "WHERE user_id=?";
    private static final String INSERT_NUMBER_BY_USER_ID = "INSERT INTO numbers values(null, ?, ?, ?)";
    private static final String UPDATE_NUMBER_BY_USER_ID_USER_PART = "UPDATE users SET number_id=? WHERE user_id=?";

    @Override
    public ArrayList<MobilPhoneNumber> searchByParameters(Object parameters) {
        throw new UnsupportedOperationException();
    }
    @Override
    public Optional<MobilPhoneNumber> searchById(long user_id) {
        Optional<MobilPhoneNumber> number;

        try(Connection connection = PoolConnection.getInstance().getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_NUMBER_BY_USER_ID);
            preparedStatement.setLong(1, user_id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                MobilPhoneNumber findNumber = MobilNumberMapper.mapMobilPhoneNumber(resultSet);
                return Optional.of(findNumber);
            }
            else {
                number = Optional.empty();
            }

        }

        catch (SQLException | PufarDAOException e) {
            return Optional.empty();
        }

        return number;
    }

    @Override
    public void updateMobilePhoneById(long userId, MobilPhoneNumber mobilPhoneNumber) throws PufarDAOException{

        try(Connection connection = PoolConnection.getInstance().getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_NUMBER_BY_USER_ID);
            preparedStatement.setString(1, mobilPhoneNumber.getCountry());
            preparedStatement.setString(2, mobilPhoneNumber.getOperator());
            preparedStatement.setString(3, mobilPhoneNumber.getNumber());
            preparedStatement.setLong(4, userId);

            preparedStatement.executeUpdate();

        }

        catch (SQLException e) {
            throw new PufarDAOException("Number wasn't updated", e);
        }

    }
    @Override
    public void insertMobilePhoneById(long userId, MobilPhoneNumber mobilPhoneNumber) throws PufarDAOException{

        try(Connection connection = PoolConnection.getInstance().getConnection()){
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

            PreparedStatement insertNumberStatement = connection.prepareStatement(INSERT_NUMBER_BY_USER_ID, Statement.RETURN_GENERATED_KEYS);
            insertNumberStatement.setString(1, mobilPhoneNumber.getCountry());
            insertNumberStatement.setString(2, mobilPhoneNumber.getOperator());
            insertNumberStatement.setString(3, mobilPhoneNumber.getNumber());

            insertNumberStatement.executeUpdate();

            // search last Auto_increment value
            ResultSet generatedKeys = insertNumberStatement.getGeneratedKeys();
            generatedKeys.next();
            long lastNumberId =  generatedKeys.getLong(1);

            PreparedStatement insertUserStatement = connection.prepareStatement(UPDATE_NUMBER_BY_USER_ID_USER_PART);
            insertUserStatement.setLong(1, lastNumberId);
            insertUserStatement.setLong(2, userId);

            insertUserStatement.executeUpdate();

            connection.commit();
        }

        catch (SQLException e) {
            throw new PufarDAOException("Number wasn't inserted", e);
        }
    }


}
