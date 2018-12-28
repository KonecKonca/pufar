package com.kozitski.pufar.dao.notification;

import com.kozitski.pufar.connection.PoolConnection;
import com.kozitski.pufar.entity.comment.NotificationComment;
import com.kozitski.pufar.entity.notification.Notification;
import com.kozitski.pufar.entity.notification.NotificationParameter;
import com.kozitski.pufar.entity.notification.UnitType;
import com.kozitski.pufar.exception.PufarDAOException;
import com.kozitski.pufar.util.CommonConstant;
import com.kozitski.pufar.util.mapper.comment.CommentMapper;
import com.kozitski.pufar.util.mapper.notification.NotificationMapper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class NotificationDaoImpl implements NotificationDao {
    private static final String SEARCH_TOP_NOTIFICATIONS_SQL =
            "SELECT n.notification_id, n.message, un.name, n.price, u.user_id, n.date, n.content, AVG(r.mark) mark FROM notifications n " +
                "LEFT JOIN units un ON n.unit_id=un.unit_id " +
                "LEFT JOIN users u ON n.user_id=u.user_id " +
                "LEFT JOIN rates r ON n.notification_id=r.notification_id " +
            "GROUP BY r.notification_id " +
            "ORDER BY n.date DESC " +
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
    private static final String SEARCH_WITH_PARAMETERS_SQL_PASSED_TIME = "n.date>=?";
    private static final String SEARCH_WITH_PARAMETERS_UNIT = "un.name=?";

    private static final String SEARCH_WITH_PARAMETERS_SQL_HIGHER_PRICE = "n.price<=?";
    private static final String SEARCH_WITH_PARAMETERS_SQL_LOWER_PRICE = "n.price>=?";
    private static final String SEARCH_WITH_PARAMETERS_HIGHER_RATE = "mark<=?";
    private static final String SEARCH_WITH_PARAMETERS_LOWER_RATE = "mark>=?";

    private static final String SEARCH_WITH_PARAMETERS_SQL_END = "GROUP BY r.notification_id";
    private static final String SEARCH_WITH_PARAMETERS_SQL_LIMIT = "LIMIT 200";
    private static final String AND = " AND ";

    private static final String SEARCH_COMMENTS_BY_NOTIFICATION_ID =
            "SELECT c.comment_id, u.login, c.comment, c.date FROM comments c " +
                "LEFT JOIN notifications n ON c.notification_id=n.notification_id " +
                "LEFT JOIN users u ON u.user_id=c.user_id " +
            "WHERE n.notification_id=? " +
            "ORDER BY c.date DESC " +
            "LIMIT 100";

    private static final String DELETE_COMMENT_BY_COMMENT_ID = "DELETE FROM comments WHERE comment_id=?";
    private static final String DELETE_NOTIFICATION_BY_NOTIFICATION_ID = "DELETE FROM notifications WHERE notification_id=?";
    private static final String DELETE_RATE_BY_NOTIFICATION_ID = "DELETE FROM rates WHERE notification_id=?";
    private static final String DELETE_COMMENT_BY_NOTIFICATION_ID = "DELETE FROM comments WHERE notification_id=?";
    private static final String CHANGE_NOTIFICATION_MESSAGE = "UPDATE notifications SET message=? WHERE notification_id=?";

    private static final String ADD_NOTIFICATION = "INSERT INTO notifications VALUES(null, ?, ?, ?, ?, ?, ?)";
    private static final String ADD_NOTIFICATION_SET_DEFAULT_RATE = "INSERT INTO rates VALUES(?, ?, ?)";
    private static final int DEFAULT_MARK = 5;

    private static final String SEARCH_NOTIFICATION_BY_UNIT_NUMBER =
            "SELECT COUNT(n.notification_id) numberNotifications FROM notifications n " +
                    "LEFT JOIN units u ON n.unit_id=u.unit_id " +
                "WHERE u.unit_id=?";
    private static final String SEARCH_NOTIFICATION_BY_UNIT_COUNT_VALUE = "numberNotifications";
    private static final String SEARCH_NOTIFICATION_BY_UNIT =
            "SELECT n.notification_id, n.message, un.name, n.price, u.user_id, n.date, n.content, AVG(r.mark) mark FROM notifications n " +
                    "LEFT JOIN units un ON n.unit_id=un.unit_id " +
                    "LEFT JOIN users u ON n.user_id=u.user_id " +
                    "LEFT JOIN rates r ON n.notification_id=r.notification_id " +
                "WHERE un.unit_id=? " +
                "GROUP BY r.notification_id " +
                "ORDER BY n.date DESC " +
                "LIMIT ?, ?";

    private static final String ADD_COMMENT = "INSERT INTO comments VALUES(null, ?, ?, ?, ?)";

    private static final String CHECK_IS_MARK_EXIST = "SELECT mark FROM rates WHERE user_id=? AND notification_id=?";
    private static final String INSERT_MARK = "INSERT INTO rates VALUES(?, ?, ?)";
    private static final String UPDATE_MARK = "UPDATE rates SET mark=? WHERE user_id=? AND notification_id=?";
    private static final String SEARCH_RATE =
            "SELECT AVG(mark) rate FROM rates " +
                "WHERE notification_id=? " +
                "GROUP BY notification_id";
    private static final String RATE = "rate";

    private static final String SEARCH_ALL_NOTIFICATIONS_BY_AUTHOR_ID =
            "SELECT n.notification_id, n.message, un.name, n.price, u.user_id, n.date, n.content, AVG(r.mark) mark FROM notifications n " +
                    "LEFT JOIN units un ON n.unit_id=un.unit_id " +
                    "LEFT JOIN users u ON n.user_id=u.user_id " +
                    "LEFT JOIN rates r ON n.notification_id=r.notification_id " +
                        "WHERE u.user_id=? " +
                    "GROUP BY r.notification_id " +
                    "ORDER BY n.date DESC " +
                    "LIMIT 50";

    @Override
    public Optional<Notification> searchById(long id) {
        return Optional.empty();
    }

    // mark
    @Override
    public double putMark(int mark, long senderId, long notificationId) throws PufarDAOException{
        try(Connection connection = PoolConnection.getInstance().getConnection()){
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            connection.setAutoCommit(false);

            PreparedStatement checkStatement = connection.prepareStatement(CHECK_IS_MARK_EXIST);
            checkStatement.setLong(1, senderId);
            checkStatement.setLong(2, notificationId);
            ResultSet checkResultSet = checkStatement.executeQuery();

            if(checkResultSet.next()){
                PreparedStatement updateStatement = connection.prepareStatement(UPDATE_MARK);
                updateStatement.setInt(1, mark);
                updateStatement.setLong(2, senderId);
                updateStatement.setLong(3, notificationId);

                updateStatement.executeUpdate();

                updateStatement.close();
            }
            else {
                PreparedStatement insertStatement = connection.prepareStatement(INSERT_MARK);
                insertStatement.setLong(1, notificationId);
                insertStatement.setLong(2, senderId);
                insertStatement.setInt(3, mark);

                insertStatement.executeUpdate();

                insertStatement.close();
            }

            checkResultSet.close();
            checkStatement.close();

            PreparedStatement findNewRateStatement = connection.prepareStatement(SEARCH_RATE);
            findNewRateStatement.setLong(1, notificationId);
            ResultSet findRateResultSet = findNewRateStatement.executeQuery();

            findRateResultSet.next();
            double rate = findRateResultSet.getDouble(RATE);

            findRateResultSet.close();
            findNewRateStatement.close();

            connection.commit();

            return rate;
        }
        catch (SQLException e) {
            throw new PufarDAOException("Mark wasn't added", e);
        }
    }
    // comments
    @Override
    public ArrayList<NotificationComment> searchCommentByNotificationId(long notificationId){

        try(Connection connection = PoolConnection.getInstance().getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_COMMENTS_BY_NOTIFICATION_ID);
            preparedStatement.setLong(1, notificationId);
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.close();
            preparedStatement.close();

            resultSet.close();
            preparedStatement.close();

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

            preparedStatement.close();

            result = true;
        }
        catch (SQLException e) {
            result = false;
        }

        return result;
    }
    @Override
    public long addComment(String comment, long senderId, long notificationId) throws PufarDAOException{
        long date = System.currentTimeMillis();

        try(Connection connection = PoolConnection.getInstance().getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_COMMENT);
            preparedStatement.setLong(1, notificationId);
            preparedStatement.setLong(2, senderId);
            preparedStatement.setString(3, comment);

            preparedStatement.setLong(4, date);

            preparedStatement.executeUpdate();

            preparedStatement.close();

        }
        catch (SQLException e) {
            throw new PufarDAOException("Comment wasn't added", e);
        }

        return date;
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

            resultSet.close();
            preparedStatement.close();

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

            resultSet.close();
            preparedStatement.close();

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
            preparedStatement.setLong(counter++, System.currentTimeMillis() - TimeUnit.MILLISECONDS.convert(parameters.getPassedTime(), TimeUnit.HOURS));
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

            dropComments.close();

            PreparedStatement dropRates = connection.prepareStatement(DELETE_COMMENT_BY_NOTIFICATION_ID);
            dropRates.setLong(1, notificationId);
            dropRates.executeUpdate();

            dropRates.close();

            // last, case foreign keys
            PreparedStatement dropNotification = connection.prepareStatement(DELETE_NOTIFICATION_BY_NOTIFICATION_ID);
            dropNotification.setLong(1, notificationId);
            dropNotification.executeUpdate();

            dropNotification.close();

            connection.commit();
            result = true;
        }
        catch (SQLException e) {
            e.printStackTrace();
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

            preparedStatement.close();

            result = true;
        }
        catch (SQLException e) {
            result = false;
        }

        return result;
    }
    @Override
    public void addNotification(Notification notification) throws PufarDAOException {

        try(Connection connection = PoolConnection.getInstance().getConnection()){
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

            PreparedStatement preparedStatement = connection.prepareStatement(ADD_NOTIFICATION, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, notification.getMessage());
            preparedStatement.setInt(2, UnitType.getUnitDBPosition(notification.getUnit()));
            preparedStatement.setDouble(3, notification.getPrice());
            preparedStatement.setLong(4, notification.getUserId());
            preparedStatement.setLong(5, notification.getDate().getTime());

            BufferedImage image = notification.getImage();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", outputStream);
            InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

            preparedStatement.setBinaryStream(6, inputStream);
            preparedStatement.executeUpdate();

            // search last Auto_increment value
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            long lastId =  generatedKeys.getLong(1);

            generatedKeys.close();
            preparedStatement.close();

            // Insert default mark
            PreparedStatement preparedStatementRate = connection.prepareStatement(ADD_NOTIFICATION_SET_DEFAULT_RATE);
            preparedStatementRate.setLong(1,lastId);
            preparedStatementRate.setLong(2, CommonConstant.SYSTEM_USER_ID);
            preparedStatementRate.setInt(3, DEFAULT_MARK);
            preparedStatementRate.executeUpdate();

            preparedStatementRate.close();

            connection.commit();
        }
        catch (SQLException e) {
            throw new PufarDAOException("Notification was'n added", e);
        }
        catch (IOException e) {
            throw new PufarDAOException("Notification was'n added, case image wasn't convert to BLOB", e);
        }

    }
    @Override
    public long searchNotificationsByUnitNumber(UnitType unitType){
        long result;

        try(Connection connection = PoolConnection.getInstance().getConnection()){

            PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_NOTIFICATION_BY_UNIT_NUMBER);
            preparedStatement.setInt(1, UnitType.getUnitDBPosition(unitType));

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            result = resultSet.getInt(SEARCH_NOTIFICATION_BY_UNIT_COUNT_VALUE);

            resultSet.close();
            preparedStatement.close();

        }
        catch (SQLException e) {
            result = 0;
        }

        return result;
    }
    @Override
    public ArrayList<Notification> searchNotificationsByUnit(UnitType unitType, int limitStart, int limitStep){
        ArrayList<Notification> result;

        try(Connection connection = PoolConnection.getInstance().getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_NOTIFICATION_BY_UNIT);
            preparedStatement.setInt(1, UnitType.getUnitDBPosition(unitType));
            preparedStatement.setInt(2, limitStart);
            preparedStatement.setInt(3, limitStep);

            ResultSet resultSet = preparedStatement.executeQuery();
            result = NotificationMapper.mapNotification(resultSet);

            resultSet.close();
            preparedStatement.close();

            for(Notification notification : result){
                notification.setComments(searchCommentByNotificationId(notification.getNotificationId()));
            }

        }
        catch (SQLException e) {
            result = new ArrayList<>();
        }

        return result;
    }
    @Override
    public ArrayList<Notification> searchAllNotificationsByAuthorId(long authorIdw){
        try(Connection connection = PoolConnection.getInstance().getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_ALL_NOTIFICATIONS_BY_AUTHOR_ID, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, authorIdw);
            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<Notification> notifications = NotificationMapper.mapNotification(resultSet);

            resultSet.close();
            preparedStatement.close();

            return notifications;
        }
        catch (SQLException e) {
            return new ArrayList<>();
        }
    }


}
