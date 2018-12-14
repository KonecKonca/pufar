package com.kozitski.pufar.dao.notification;

import com.kozitski.pufar.connection.PoolConnection;
import com.kozitski.pufar.entity.notification.Notification;
import com.kozitski.pufar.entity.notification.NotificationParameters;
import com.kozitski.pufar.util.mapper.notification.NotificationMapper;

import java.sql.*;
import java.util.ArrayList;

public class NotificationDaoImpl implements NotificationDao {
    private static final String SEARCH_TOP_NOTIFICATIONS_SQL =
            "SELECT n.notification_id, n.message, un.name, n.price, u.user_id, n.date, AVG(r.mark) mark FROM notifications n " +
                "LEFT JOIN units un ON n.unit_id=un.unit_id " +
                "LEFT JOIN users u ON n.user_id=u.user_id " +
                "LEFT JOIN rates r ON n.notification_id=r.notification_id " +
            "GROUP BY r.notification_id " +
            "ORDER BY mark ASC " +
            "LIMIT ?";

    // for search with parameters
    private static final String SEARCH_WITH_PARAMETERS_SQL_START =
            "SELECT n.notification_id, n.message, un.name, n.price, u.user_id, n.date, AVG(r.mark) mark FROM notifications n " +
                "INNER JOIN units un ON n.unit_id=un.unit_id " +
                "INNER JOIN users u ON n.user_id=u.user_id " +
                "LEFT JOIN rates r ON n.notification_id=r.notification_id " +
            "WHERE ";
    private static final String SEARCH_WITH_PARAMETERS_SQL_ID = "n.notification_id=?";
    private static final String SEARCH_WITH_PARAMETERS_SQL_SENDER_ID = "u.user_id=?";
    private static final String SEARCH_WITH_PARAMETERS_SQL_PASSED_TIME = "n.date>=?";
    private static final String SEARCH_WITH_PARAMETERS_UNIT = "un.name=?";

    private static final String SEARCH_WITH_PARAMETERS_SQL_HIGHER_PRICE = "n.price<=?";
    private static final String SEARCH_WITH_PARAMETERS_SQL_LOWER_PRICE = "n.price>=?";
    private static final String SEARCH_WITH_PARAMETERS_HIGHER_RATE = "mark<=?";
    private static final String SEARCH_WITH_PARAMETERS_LOWER_RATE = "mark>=?";

    private static final String SEARCH_WITH_PARAMETERS_SQL_END = "GROUP BY r.notification_id";
    private static final String AND = " AND ";
    private static final int HOUR_TO_MS = 3_600_000;


    @Override
    public ArrayList<Notification> searchTopNotificationsWithLimit(int limit){

        try(Connection connection = PoolConnection.getInstance().getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_TOP_NOTIFICATIONS_SQL);
            preparedStatement.setLong(1, limit);
            ResultSet resultSet = preparedStatement.executeQuery();

            return NotificationMapper.mapNotification(resultSet);
        }

        catch (SQLException e) {
            return new ArrayList<>();
        }

    }

    // todo: ask opinion about my decision
    @Override
    public ArrayList<Notification> searchNotificationByParameters(NotificationParameters parameters) {

        try(Connection connection = PoolConnection.getInstance().getConnection()){
            String parametersSql = generateSearchWithParametersSql(parameters);

            PreparedStatement preparedStatement = connection.prepareStatement(parametersSql);
            fullFillPreparedStatement(preparedStatement, parameters);

            ResultSet resultSet = preparedStatement.executeQuery();

            return NotificationMapper.mapNotification(resultSet);
        }

        catch (SQLException e) {
            return new ArrayList<>();
        }

    }


    // here methods get contract on each other (their use )
    private String generateSearchWithParametersSql(NotificationParameters parameters){
        StringBuilder addSql = new StringBuilder();
        addSql.append(SEARCH_WITH_PARAMETERS_SQL_START);

        ArrayList<String> whereConditions = new ArrayList<>();
        if(parameters.getNotificationId() != null){
            whereConditions.add(SEARCH_WITH_PARAMETERS_SQL_ID);
        }
        if(parameters.getSenderId() != null){
            whereConditions.add(SEARCH_WITH_PARAMETERS_SQL_SENDER_ID);
        }
        if(parameters.getPassedTime() != null){
            whereConditions.add(SEARCH_WITH_PARAMETERS_SQL_PASSED_TIME);
        }
        if(parameters.getUnitType() != null){
            whereConditions.add(SEARCH_WITH_PARAMETERS_UNIT);
        }

        if(parameters.getHigherPrice() != null){
            whereConditions.add(SEARCH_WITH_PARAMETERS_SQL_HIGHER_PRICE);
        }
        if(parameters.getLowerPrice() != null){
            whereConditions.add(SEARCH_WITH_PARAMETERS_SQL_LOWER_PRICE);
        }
        if(parameters.getHigherRate() != null){
            whereConditions.add(SEARCH_WITH_PARAMETERS_HIGHER_RATE);
        }
        if(parameters.getLowerRate() != null){
            whereConditions.add(SEARCH_WITH_PARAMETERS_LOWER_RATE);
        }

        for (int i = 0; i < whereConditions.size() - 1; i++) {
            addSql.append(whereConditions.get(i));
            addSql.append(AND);
        }
        addSql.append(whereConditions.get(whereConditions.size() - 1));
        addSql.append(" ");
        addSql.append(SEARCH_WITH_PARAMETERS_SQL_END);

        return addSql.toString();
    }
    private void fullFillPreparedStatement(PreparedStatement preparedStatement, NotificationParameters parameters) throws SQLException {
        int counter = 1;

        if(parameters.getNotificationId() != null){
            preparedStatement.setLong(counter++, parameters.getNotificationId());
        }
        if(parameters.getSenderId() != null){
            preparedStatement.setLong(counter++, parameters.getSenderId());
        }
        if(parameters.getPassedTime() != null){
            java.util.Date date = new java.util.Date();
//            System.out.println(new Timestamp(date.getTime() - HOUR_TO_MS * parameters.getPassedTime()));

            preparedStatement.setTimestamp(counter++, new Timestamp(date.getTime() - HOUR_TO_MS * parameters.getPassedTime()));
        }
        if(parameters.getUnitType() != null){
            preparedStatement.setString(counter++, parameters.getUnitType().name().toLowerCase());
        }

        if(parameters.getHigherPrice() != null){
            preparedStatement.setDouble(counter++, parameters.getHigherPrice());
        }
        if(parameters.getLowerPrice() != null){
            preparedStatement.setDouble(counter++, parameters.getLowerPrice());
        }
        if(parameters.getHigherRate() != null){
            preparedStatement.setDouble(counter++, parameters.getHigherRate());
        }
        if(parameters.getLowerRate() != null){
            preparedStatement.setDouble(counter, parameters.getLowerRate());
        }

    }


}
