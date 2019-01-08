package com.kozitski.pufar.util.mapper.dialog;

import com.kozitski.pufar.entity.message.UserMessage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DialogMapper {

    private static final String SENDER_LOGIN = "sender_login";
    private static final String RECEIVER_LOGIN = "receiver_login";
    private static final String MESSAGE = "message";
    private static final String DATE = "date";

    private DialogMapper() { }

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
