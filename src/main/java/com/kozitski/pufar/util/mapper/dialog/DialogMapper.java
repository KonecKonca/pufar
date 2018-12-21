package com.kozitski.pufar.util.mapper.dialog;

import com.kozitski.pufar.entity.message.UserMessage;
import com.kozitski.pufar.util.CommonConstant;

import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class DialogMapper {

    private static final String SENDER_LOGIN = "sender_login";
    private static final String RECEIVER_LOGIN = "receiver_login";
    private static final String MESSAGE = "message";
    private static final String DATE = "date";


    public static ArrayList<UserMessage> createMessages(ResultSet resultSet) throws SQLException {
        ArrayList<UserMessage> result = new ArrayList<>();

        while (resultSet.next()){

            String sender_login = resultSet.getString(SENDER_LOGIN);
            String receiver_login = resultSet.getString(RECEIVER_LOGIN);
            String message = resultSet.getString(MESSAGE);
            Date date = new Date(resultSet.getLong(DATE));

            result.add(new UserMessage(message, date, sender_login, receiver_login));
        }

        return result;
    }

}
