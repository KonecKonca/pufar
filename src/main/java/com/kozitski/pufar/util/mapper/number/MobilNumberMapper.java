package com.kozitski.pufar.util.mapper.number;

import com.kozitski.pufar.entity.number.MobilPhoneNumber;
import com.kozitski.pufar.exception.PufarDAOException;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The Class MobilNumberMapper.
 * Maps ResultSet into MobilPhoneNumber.
 */
public class MobilNumberMapper {
    
    /** The Constant NUMBER_ID. */
    private static final String NUMBER_ID = "n.number_id";
    
    /** The Constant NUMBER_COUNTRY. */
    private static final String NUMBER_COUNTRY = "n.country";
    
    /** The Constant NUMBER_OPERATOR. */
    private static final String NUMBER_OPERATOR = "n.operator";
    
    /** The Constant NUMBER_VALUE. */
    private static final String NUMBER_VALUE = "n.number";

    /**
     * Instantiates a new mobil number mapper.
     */
    private MobilNumberMapper() { }

    /**
     * Map mobil phone number.
     *
     * @param resultSet the result set
     * @return the mobil phone number
     * @throws PufarDAOException the pufar DAO exception
     */
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
