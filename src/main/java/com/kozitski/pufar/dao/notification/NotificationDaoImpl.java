package com.kozitski.pufar.dao.notification;

import com.kozitski.pufar.connection.PoolConnection;
import com.kozitski.pufar.entity.notification.Notification;
import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.service.notification.NotificationService;
import com.kozitski.pufar.util.mapper.notification.NotificationMapper;
import com.kozitski.pufar.util.mapper.user.UserMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class NotificationDaoImpl implements NotificationDao {
    private static final String SEARCH_TOP_NOTIFICATIONS_SQL = "SELECT * FROM notifications";

    public ArrayList<Notification> searchTopNotificationsWithLimit(int limit){

        try(Connection connection = PoolConnection.getInstance().getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_TOP_NOTIFICATIONS_SQL);
//            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            return NotificationMapper.searchTopNotificationsWithLimit(resultSet);
        }

        catch (SQLException e) {
            return new ArrayList<>();
        }

    }


}
