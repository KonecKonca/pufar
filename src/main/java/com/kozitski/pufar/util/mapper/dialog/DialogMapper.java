package com.kozitski.pufar.util.mapper.dialog;

import com.kozitski.pufar.entity.message.UserMessage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The Class DialogMapper.
 * Maps ResultSet into UserMessage.
 */
public class DialogMapper {

    /** The Constant SENDER_LOGIN. */
    private static final String SENDER_LOGIN = "sender_login";
    
    /** The Constant RECEIVER_LOGIN. */
    private static final String RECEIVER_LOGIN = "receiver_login";
    
    /** The Constant MESSAGE. */
    private static final String MESSAGE = "message";
    
    /** The Constant DATE. */
    private static final String DATE = "date";

    /**
     * Instantiates a new dialog mapper.
     */
    private DialogMapper() { }

    /**
     * Creates the messages.
     *
     * @param resultSet the result set
     * @return the list
     * @throws SQLException the SQL exception
     */
    public static List<UserMessage> createMessages(ResultSet resultSet) throws SQLException {
        ArrayList<UserMessage> result = new ArrayList<>();

        while (resultSet.next()) {

            String senderLogin = resultSet.getString(SENDER_LOGIN);
            String receiverLogin = resultSet.getString(RECEIVER_LOGIN);
            String message = resultSet.getString(MESSAGE);
            Date date = new Date(resultSet.getLong(DATE));

            result.add(new UserMessage(message, date, senderLogin, receiverLogin));
        }

        return result;
    }

}
