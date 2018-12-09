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
    private static final String SEARCH_TOP_NOTIFICATIONS_SQL =
            "SELECT n.notification_id, n.message, un.name, n.price, u.user_id, n.date, AVG(r.mark) mark FROM notifications n " +
                "LEFT JOIN units un ON n.unit_id=un.unit_id " +
                "LEFT JOIN users u ON n.user_id=u.user_id " +
                "LEFT JOIN rates r ON n.notification_id=r.notification_id " +
            "GROUP BY r.notification_id " +
            "ORDER BY mark ASC " +
            "LIMIT ?";

    public ArrayList<Notification> searchTopNotificationsWithLimit(int limit){

        try(Connection connection = PoolConnection.getInstance().getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_TOP_NOTIFICATIONS_SQL);
            preparedStatement.setLong(1, limit);
            ResultSet resultSet = preparedStatement.executeQuery();

            return NotificationMapper.searchTopNotificationsWithLimit(resultSet);
        }

        catch (SQLException e) {
            return new ArrayList<>();
        }

    }


}
