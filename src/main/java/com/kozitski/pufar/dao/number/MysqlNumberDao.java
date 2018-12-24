package com.kozitski.pufar.dao.number;

import com.kozitski.pufar.connection.PoolConnection;
import com.kozitski.pufar.entity.number.MobilPhoneNumber;
import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.exception.PufarDAOException;
import com.kozitski.pufar.util.mapper.number.MobilNumberMapper;
import com.kozitski.pufar.util.mapper.user.UserMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class MysqlNumberDao implements NumberDao {

    private static final String SEARCH_NUMBER_BY_USER_ID =
            "SELECT n.number_id, n.country, n.operator, n.number FROM numbers n " +
                "INNER JOIN users u ON u.number_id=n.number_id " +
            "WHERE u.user_id=?";

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

}
