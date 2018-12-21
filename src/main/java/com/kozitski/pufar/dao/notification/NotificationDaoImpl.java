package com.kozitski.pufar.dao.notification;

import com.kozitski.pufar.connection.PoolConnection;
import com.kozitski.pufar.entity.comment.NotificationComment;
import com.kozitski.pufar.entity.notification.Notification;
import com.kozitski.pufar.entity.notification.NotificationParameter;
import com.kozitski.pufar.exception.PufarDAOException;
import com.kozitski.pufar.util.mapper.comment.CommentMapper;
import com.kozitski.pufar.util.mapper.notification.NotificationMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class NotificationDaoImpl implements NotificationDao {
    private static final String SEARCH_TOP_NOTIFICATIONS_SQL =
            "SELECT n.notification_id, n.message, un.name, n.price, u.user_id, n.date, n.content, AVG(r.mark) mark FROM notifications n " +
                "LEFT JOIN units un ON n.unit_id=un.unit_id " +
                "LEFT JOIN users u ON n.user_id=u.user_id " +
                "LEFT JOIN rates r ON n.notification_id=r.notification_id " +
            "GROUP BY r.notification_id " +
            "ORDER BY mark ASC " +
            "LIMIT ?";

    // for search with parameters
    private static final String SEARCH_WITH_PARAMETERS_SQL_START =
            "SELECT n.notification_id, n.message, un.name, n.price, u.user_id, n.content, n.date, AVG(r.mark) mark FROM notifications n " +
                "INNER JOIN units un ON n.unit_id=un.unit_id " +
                "INNER JOIN users u ON n.user_id=u.user_id " +
                "LEFT JOIN rates r ON n.notification_id=r.notification_id ";
    private static final String SEARCH_WITH_PARAMETERS_SQL_WHERE = "WHERE ";

    private static final String SEARCH_WITH_PARAMETERS_SQL_ID = "n.notification_id=?";
    private static final String SEARCH_WITH_PARAMETERS_SQL_SENDER_ID = "u.user_id=?";
    private static final String SEARCH_WITH_PARAMETERS_SQL_PASSED_TIME = "n.date>=DATE_ADD(NOW(), INTERVAL -? HOUR)";
    private static final String SEARCH_WITH_PARAMETERS_UNIT = "un.name=?";

    private static final String SEARCH_WITH_PARAMETERS_SQL_HIGHER_PRICE = "n.price<=?";
    private static final String SEARCH_WITH_PARAMETERS_SQL_LOWER_PRICE = "n.price>=?";
    private static final String SEARCH_WITH_PARAMETERS_HIGHER_RATE = "mark<=?";
    private static final String SEARCH_WITH_PARAMETERS_LOWER_RATE = "mark>=?";

    private static final String SEARCH_WITH_PARAMETERS_SQL_END = "GROUP BY r.notification_id";
    private static final String SEARCH_WITH_PARAMETERS_SQL_LIMIT = "LIMIT 200";
    private static final String AND = " AND ";

    private static final String SEARCH_COMMENTS_BY_NOTIFICATION_ID =
            "SELECT c.comment_id, u.login, c.comment FROM comments c " +
                "LEFT JOIN notifications n ON c.notification_id=n.notification_id " +
                "LEFT JOIN users u ON u.user_id=c.user_id " +
            "WHERE n.notification_id=? " +
            "LIMIT 100";

    private static final String DELETE_COMMENT_BY_COMMENT_ID = "DELETE FROM comments WHERE comment_id=?";
    private static final String DELETE_NOTIFICATION_BY_NOTIFICATION_ID = "DELETE FROM notifications WHERE notification_id=?";
    private static final String DELETE_RATE_BY_NOTIFICATION_ID = "DELETE FROM rates WHERE notification_id=?";
    private static final String DELETE_COMMENT_BY_NOTIFICATION_ID = "DELETE FROM comments WHERE notification_id=?";
    private static final String CHANGE_NOTIFICATION_MESSAGE = "UPDATE notifications SET message=? WHERE notification_id=?";


    @Override
    public Optional<Notification> searchById(long id) {
        return Optional.empty();
    }

    // comments
    @Override
    public ArrayList<NotificationComment> searchCommentByNotificationId(long notificationId){

        try(Connection connection = PoolConnection.getInstance().getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_COMMENTS_BY_NOTIFICATION_ID);
            preparedStatement.setLong(1, notificationId);
            ResultSet resultSet = preparedStatement.executeQuery();

            return CommentMapper.mapComments(resultSet);
        }

        catch (SQLException e) {
            return new ArrayList<>();
        }
    }
    @Override
    public boolean dropCommentById(long commentId) {
        boolean result;

        try(Connection connection = PoolConnection.getInstance().getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_COMMENT_BY_COMMENT_ID);
            preparedStatement.setLong(1, commentId);
            preparedStatement.executeUpdate();

            result = true;
        }
        catch (SQLException e) {
            result = false;
        }

        return result;
    }

    @Override
    public ArrayList<Notification> searchTopNotificationsWithLimit(int limit){

        try(Connection connection = PoolConnection.getInstance().getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_TOP_NOTIFICATIONS_SQL);
            preparedStatement.setLong(1, limit);
            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<Notification> notifications = NotificationMapper.mapNotification(resultSet);
            for(Notification notification : notifications){
                ArrayList<NotificationComment> notificationComments = searchCommentByNotificationId(notification.getNotificationId());
                notification.setComments(notificationComments);
            }

            return notifications;
        }

        catch (SQLException e) {
            return new ArrayList<>();
        }

    }
    // todo: ask opinion about my decision
    @Override
    public ArrayList<Notification> searchByParameters(NotificationParameter parameters) {

        try(Connection connection = PoolConnection.getInstance().getConnection()){
            String parametersSql = generateSearchWithParametersSql(parameters);

            PreparedStatement preparedStatement = connection.prepareStatement(parametersSql);
            fullFillPreparedStatement(preparedStatement, parameters);

            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<Notification> notifications = NotificationMapper.mapNotification(resultSet);
            for(Notification notification : notifications){
                ArrayList<NotificationComment> notificationComments = searchCommentByNotificationId(notification.getNotificationId());
                notification.setComments(notificationComments);
            }

            return notifications;
        }

        catch (SQLException e) {
            return new ArrayList<>();
        }

    }
    // here methods get contract on each other (their use same order of argument)
    private String generateSearchWithParametersSql(NotificationParameter parameters){
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

        if(whereConditions.size() != 0){
            addSql.append(SEARCH_WITH_PARAMETERS_SQL_WHERE);
            for (int i = 0; i < whereConditions.size() - 1; i++) {
                addSql.append(whereConditions.get(i));
                addSql.append(AND);
            }
            addSql.append(whereConditions.get(whereConditions.size() - 1));
            addSql.append(" ");
            addSql.append(SEARCH_WITH_PARAMETERS_SQL_END);
        }
        else {
            addSql.append(SEARCH_WITH_PARAMETERS_SQL_END);
            addSql.append(" ");
            addSql.append(SEARCH_WITH_PARAMETERS_SQL_LIMIT);
        }

        return addSql.toString();
    }
    private void fullFillPreparedStatement(PreparedStatement preparedStatement, NotificationParameter parameters) throws SQLException {
        int counter = 1;

        if(parameters.getNotificationId() != null){
            preparedStatement.setLong(counter++, parameters.getNotificationId());
        }
        if(parameters.getSenderId() != null){
            preparedStatement.setLong(counter++, parameters.getSenderId());
        }
        if(parameters.getPassedTime() != null){
            preparedStatement.setInt(counter++, parameters.getPassedTime());
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
    @Override
    public boolean dropNotificationById(long notificationId) {
        boolean result;

        try(Connection connection = PoolConnection.getInstance().getConnection()){
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            connection.setAutoCommit(false);

            PreparedStatement dropComments = connection.prepareStatement(DELETE_RATE_BY_NOTIFICATION_ID);
            dropComments.setLong(1, notificationId);
            dropComments.executeUpdate();

            PreparedStatement dropRates = connection.prepareStatement(DELETE_COMMENT_BY_NOTIFICATION_ID);
            dropRates.setLong(1, notificationId);
            dropRates.executeUpdate();

            // last, case foreigen keys
            PreparedStatement dropNotification = connection.prepareStatement(DELETE_NOTIFICATION_BY_NOTIFICATION_ID);
            dropNotification.setLong(1, notificationId);
            dropNotification.executeUpdate();

            connection.commit();
            result = true;
        }
        catch (SQLException e) {
            result = false;
        }

        return result;
    }
    @Override
    public boolean changeNotificationMessage(long notificationId, String newMessage) {
        boolean result;

        try(Connection connection = PoolConnection.getInstance().getConnection()){

            PreparedStatement preparedStatement = connection.prepareStatement(CHANGE_NOTIFICATION_MESSAGE);
            preparedStatement.setString(1, newMessage);
            preparedStatement.setLong(2, notificationId);
            preparedStatement.executeUpdate();

            result = true;
        }
        catch (SQLException e) {
            result = false;
        }

        return result;
    }

    @Override
    public void addNotification(Notification notification) throws PufarDAOException {
        System.out.println(notification);
    }


}
