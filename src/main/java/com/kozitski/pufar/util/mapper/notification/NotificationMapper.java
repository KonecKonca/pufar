package com.kozitski.pufar.util.mapper.notification;

import com.kozitski.pufar.entity.notification.Notification;
import com.kozitski.pufar.entity.notification.UnitType;
import com.kozitski.pufar.util.CommonConstant;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class NotificationMapper  {
    private static final String NOTIFICATION_ID = "n.notification_id";
    private static final String NOTIFICATION_MESSAGE = "n.message";
    private static final String NOTIFICATION_UNIT = "un.name";
    private static final String NOTIFICATION_PRICE = "n.price";
    private static final String NOTIFICATION_SENDER_ID = "u.user_id";
    private static final String NOTIFICATION_DATE = "n.date";
    private static final String NOTIFICATION_MARK = "mark";

    public static ArrayList<Notification> mapNotification(ResultSet resultSet) throws SQLException {
        ArrayList<Notification> result = new ArrayList<>();

        while (resultSet.next()){
            Notification notification = new Notification();

            notification.setNotificationId(resultSet.getLong(NOTIFICATION_ID));
            notification.setMessage(resultSet.getString(NOTIFICATION_MESSAGE));
            notification.setUnit(UnitType.valueOf(resultSet.getString(NOTIFICATION_UNIT).toUpperCase()));
            notification.setPrice(resultSet.getDouble(NOTIFICATION_PRICE));

            notification.setUserId(resultSet.getLong(NOTIFICATION_SENDER_ID));
            notification.setDate(resultSet.getDate(NOTIFICATION_DATE));
            notification.setTime(new Time(resultSet.getTime(NOTIFICATION_DATE).getTime()));
            notification.setRate(resultSet.getDouble(NOTIFICATION_MARK));


            result.add(notification);
        }

        return result;
    }

}
