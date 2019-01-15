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

/**
 * The Class NumberDaoImpl.
 * Implementation of NumberDao on MySQL
 */
public class NumberDaoImpl implements NumberDao {
    
    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(NumberDaoImpl.class);

    /** The Constant SEARCH_NUMBER_BY_USER_ID. */
    private static final String SEARCH_NUMBER_BY_USER_ID =
            "SELECT n.number_id, n.country, n.operator, n.number FROM numbers n " +
                    "INNER JOIN users u ON u.number_id=n.number_id " +
                    "WHERE u.user_id=?";

    /** The Constant UPDATE_NUMBER_BY_USER_ID. */
    private static final String UPDATE_NUMBER_BY_USER_ID =
            "UPDATE numbers n " +
                    "LEFT JOIN users u ON n.number_id=u.number_id " +
                    "SET country=?, operator=?, number=?" +
                    "WHERE user_id=?";
    
    /** The Constant INSERT_NUMBER_BY_USER_ID. */
    private static final String INSERT_NUMBER_BY_USER_ID = "INSERT INTO numbers values(null, ?, ?, ?)";
    
    /** The Constant UPDATE_NUMBER_BY_USER_ID_USER_PART. */
    private static final String UPDATE_NUMBER_BY_USER_ID_USER_PART = "UPDATE users SET number_id=? WHERE user_id=?";

    /**
     * Search by parameters.
     *
     * @param parameters the parameters
     * @return the list
     */
    @Override
    public List<MobilPhoneNumber> searchByParameters(Object parameters) {
        throw new UnsupportedOperationException();
    }

    /**
     * Search by id.
     *
     * @param userId the user id
     * @return the optional
     */
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

    /**
     * Update mobile phone by id.
     *
     * @param userId the user id
     * @param mobilPhoneNumber the mobil phone number
     * @throws PufarDAOException the pufar DAO exception
     */
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

    /**
     * Insert mobile phone by id.
     *
     * @param userId the user id
     * @param mobilPhoneNumber the mobil phone number
     * @throws PufarDAOException the pufar DAO exception
     */
    @Override
    public void insertMobilePhoneById(long userId, MobilPhoneNumber mobilPhoneNumber) throws PufarDAOException {

        PreparedStatement insertNumberStatement = null;
        PreparedStatement insertUserStatement = null;
        ResultSet generatedKeys = null;

        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
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
            try { connection.rollback(); } catch (SQLException e1) { LOGGER.error(PufarDaoConstant.ROLLBACK_LOG); }
            throw new PufarDAOException("Number wasn't inserted", e);
        } finally {
            try { DbUtils.close(generatedKeys); } catch (SQLException e) { LOGGER.error(PufarDaoConstant.CLOSE_RESULTSET_ERROR_LOG); }

            try { DbUtils.close(insertUserStatement); } catch (SQLException e) { LOGGER.error(PufarDaoConstant.CLOSE_STATEMENT_ERROR_LOG); }
            try { DbUtils.close(insertNumberStatement); } catch (SQLException e) { LOGGER.error(PufarDaoConstant.CLOSE_STATEMENT_ERROR_LOG); }

            try { DbUtils.close(connection); } catch (SQLException e) { LOGGER.error(PufarDaoConstant.CLOSE_CONNECTION_LOG); }
        }

    }


}
