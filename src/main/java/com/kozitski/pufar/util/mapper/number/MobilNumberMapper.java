package com.kozitski.pufar.util.mapper.number;

import com.kozitski.pufar.entity.number.MobilPhoneNumber;
import com.kozitski.pufar.exception.PufarDAOException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MobilNumberMapper {
    private static final String NUMBER_ID = "n.number_id";
    private static final String NUMBER_COUNTRY = "n.country";
    private static final String NUMBER_OPERATOR = "n.operator";
    private static final String NUMBER_VALUE = "n.number";

    private MobilNumberMapper() { }

    public static MobilPhoneNumber mapMobilPhoneNumber(ResultSet resultSet) throws PufarDAOException {
        try {
            MobilPhoneNumber phoneNumber = new MobilPhoneNumber();

            long numberId = resultSet.getLong(NUMBER_ID);
            phoneNumber.setMobilPhoneNumberId(numberId);

            String country = resultSet.getString(NUMBER_COUNTRY);
            phoneNumber.setCountry(country);

            String operator = resultSet.getString(NUMBER_OPERATOR);
            phoneNumber.setOperator(operator);

            String value = resultSet.getString(NUMBER_VALUE);
            phoneNumber.setNumber(value);

            return phoneNumber;
        } catch (SQLException e) {
            throw new PufarDAOException("MobilPhone is not created", e);
        }

    }

}
