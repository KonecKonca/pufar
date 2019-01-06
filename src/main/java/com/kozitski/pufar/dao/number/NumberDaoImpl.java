package com.kozitski.pufar.dao.number;

import com.kozitski.pufar.dao.PufarDaoConstant;
import com.kozitski.pufar.database.ConnectionPool;
import com.kozitski.pufar.entity.number.MobilPhoneNumber;
import com.kozitski.pufar.exception.PufarDAOException;
import com.kozitski.pufar.util.mapper.number.MobilNumberMapper;
import org.apache.commons.dbutils.DbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class NumberDaoImpl implements NumberDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(NumberDaoImpl.class);

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
    public List<MobilPhoneNumber> searchByParameters(Object parameters) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<MobilPhoneNumber> searchById(long userId) {
        Optional<MobilPhoneNumber> number;

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            preparedStatement = connection.prepareStatement(SEARCH_NUMBER_BY_USER_ID);
            preparedStatement.setLong(1, userId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                MobilPhoneNumber findNumber = MobilNumberMapper.mapMobilPhoneNumber(resultSet);
                return Optional.of(findNumber);
            } else {
                number = Optional.empty();
            }

        } catch (SQLException | PufarDAOException e) {
            return Optional.empty();
        } finally {
            try {
                DbUtils.close(resultSet);
            } catch (SQLException e) {
                LOGGER.error(PufarDaoConstant.CLOSE_RESULTSET_ERROR_LOG);
            }
            try {
                DbUtils.close(preparedStatement);
            } catch (SQLException e) {
                LOGGER.error(PufarDaoConstant.CLOSE_STATEMENT_ERROR_LOG);
            }
        }

        return number;
    }

    @Override
    public void updateMobilePhoneById(long userId, MobilPhoneNumber mobilPhoneNumber) throws PufarDAOException {

        PreparedStatement preparedStatement = null;

        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            preparedStatement = connection.prepareStatement(UPDATE_NUMBER_BY_USER_ID);
            preparedStatement.setString(1, mobilPhoneNumber.getCountry());
            preparedStatement.setString(2, mobilPhoneNumber.getOperator());
            preparedStatement.setString(3, mobilPhoneNumber.getNumber());
            preparedStatement.setLong(4, userId);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new PufarDAOException("Number wasn't updated", e);
        } finally {
            try {
                DbUtils.close(preparedStatement);
            } catch (SQLException e) {
                LOGGER.error(PufarDaoConstant.CLOSE_STATEMENT_ERROR_LOG);
            }
        }

    }

    @Override
    public void insertMobilePhoneById(long userId, MobilPhoneNumber mobilPhoneNumber) throws PufarDAOException {

        PreparedStatement insertNumberStatement = null;
        PreparedStatement insertUserStatement = null;
        ResultSet generatedKeys = null;

        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            connection.setAutoCommit(false);

            insertNumberStatement = connection.prepareStatement(INSERT_NUMBER_BY_USER_ID, Statement.RETURN_GENERATED_KEYS);
            insertNumberStatement.setString(1, mobilPhoneNumber.getCountry());
            insertNumberStatement.setString(2, mobilPhoneNumber.getOperator());
            insertNumberStatement.setString(3, mobilPhoneNumber.getNumber());

            insertNumberStatement.executeUpdate();

            // search last Auto_increment value
            generatedKeys = insertNumberStatement.getGeneratedKeys();
            generatedKeys.next();
            long lastNumberId = generatedKeys.getLong(1);

            insertUserStatement = connection.prepareStatement(UPDATE_NUMBER_BY_USER_ID_USER_PART);
            insertUserStatement.setLong(1, lastNumberId);
            insertUserStatement.setLong(2, userId);

            insertUserStatement.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            throw new PufarDAOException("Number wasn't inserted", e);
        } finally {
            try {
                DbUtils.close(generatedKeys);
            } catch (SQLException e) {
                LOGGER.error(PufarDaoConstant.CLOSE_RESULTSET_ERROR_LOG);
            }

            try {
                DbUtils.close(insertUserStatement);
            } catch (SQLException e) {
                LOGGER.error(PufarDaoConstant.CLOSE_STATEMENT_ERROR_LOG);
            }
            try {
                DbUtils.close(insertNumberStatement);
            } catch (SQLException e) {
                LOGGER.error(PufarDaoConstant.CLOSE_STATEMENT_ERROR_LOG);
            }
        }

    }


}
