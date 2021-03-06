package com.kozitski.pufar.dao.notification;

import com.kozitski.pufar.database.ConnectionPool;
import com.kozitski.pufar.dao.PufarDaoConstant;
import com.kozitski.pufar.entity.comment.NotificationComment;
import com.kozitski.pufar.entity.notification.Notification;
import com.kozitski.pufar.entity.notification.NotificationParameter;
import com.kozitski.pufar.entity.notification.UnitType;
import com.kozitski.pufar.exception.PufarDAOException;
import com.kozitski.pufar.util.CommonConstant;
import com.kozitski.pufar.util.mapper.comment.CommentMapper;
import com.kozitski.pufar.util.mapper.notification.NotificationMapper;
import org.apache.commons.dbutils.DbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * The Class NotificationDaoImpl.
 * Implementation of NotificationDao on MySQL
 */
public class NotificationDaoImpl implements NotificationDao {
    
    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationDaoImpl.class);

    /** The Constant SEARCH_TOP_NOTIFICATIONS_SQL. */
    private static final String SEARCH_TOP_NOTIFICATIONS_SQL =
            "SELECT n.notification_id, n.message, un.name, n.price, u.user_id, n.date, n.content, AVG(r.mark) mark FROM notifications n " +
                "LEFT JOIN units un ON n.unit_id=un.unit_id " +
                "LEFT JOIN users u ON n.user_id=u.user_id " +
                "LEFT JOIN rates r ON n.notification_id=r.notification_id " +
            "GROUP BY r.notification_id " +
            "ORDER BY n.date DESC " +
            "LIMIT ?";

    /** The Constant SEARCH_WITH_PARAMETERS_SQL_START. */
    // for search with parameters
    private static final String SEARCH_WITH_PARAMETERS_SQL_START =
            "SELECT n.notification_id, n.message, un.name, n.price, u.user_id, n.content, n.date, AVG(r.mark) mark FROM notifications n " +
                "INNER JOIN units un ON n.unit_id=un.unit_id " +
                "INNER JOIN users u ON n.user_id=u.user_id " +
                "LEFT JOIN rates r ON n.notification_id=r.notification_id ";
    
    /** The Constant SEARCH_WITH_PARAMETERS_SQL_WHERE. */
    private static final String SEARCH_WITH_PARAMETERS_SQL_WHERE = "WHERE ";

    /** The Constant SEARCH_WITH_PARAMETERS_SQL_ID. */
    private static final String SEARCH_WITH_PARAMETERS_SQL_ID = "n.notification_id=?";
    
    /** The Constant SEARCH_WITH_PARAMETERS_SQL_SENDER_ID. */
    private static final String SEARCH_WITH_PARAMETERS_SQL_SENDER_ID = "u.user_id=?";
    
    /** The Constant SEARCH_WITH_PARAMETERS_SQL_PASSED_TIME. */
    private static final String SEARCH_WITH_PARAMETERS_SQL_PASSED_TIME = "n.date>=?";
    
    /** The Constant SEARCH_WITH_PARAMETERS_UNIT. */
    private static final String SEARCH_WITH_PARAMETERS_UNIT = "un.name=?";

    /** The Constant SEARCH_WITH_PARAMETERS_SQL_HIGHER_PRICE. */
    private static final String SEARCH_WITH_PARAMETERS_SQL_HIGHER_PRICE = "n.price<=?";
    
    /** The Constant SEARCH_WITH_PARAMETERS_SQL_LOWER_PRICE. */
    private static final String SEARCH_WITH_PARAMETERS_SQL_LOWER_PRICE = "n.price>=?";
    
    /** The Constant SEARCH_WITH_PARAMETERS_HIGHER_RATE. */
    private static final String SEARCH_WITH_PARAMETERS_HIGHER_RATE = "mark<=?";
    
    /** The Constant SEARCH_WITH_PARAMETERS_LOWER_RATE. */
    private static final String SEARCH_WITH_PARAMETERS_LOWER_RATE = "mark>=?";

    /** The Constant SEARCH_WITH_PARAMETERS_SQL_END. */
    private static final String SEARCH_WITH_PARAMETERS_SQL_END = "GROUP BY r.notification_id";
    
    /** The Constant SEARCH_WITH_PARAMETERS_SQL_LIMIT. */
    private static final String SEARCH_WITH_PARAMETERS_SQL_LIMIT = "LIMIT 200";
    
    /** The Constant AND. */
    private static final String AND = " AND ";

    /** The Constant SEARCH_COMMENTS_BY_NOTIFICATION_ID. */
    private static final String SEARCH_COMMENTS_BY_NOTIFICATION_ID =
            "SELECT c.comment_id, u.login, c.comment, c.date FROM comments c " +
                "LEFT JOIN notifications n ON c.notification_id=n.notification_id " +
                "LEFT JOIN users u ON u.user_id=c.user_id " +
            "WHERE n.notification_id=? " +
            "ORDER BY c.date DESC " +
            "LIMIT 100";

    /** The Constant DELETE_COMMENT_BY_COMMENT_ID. */
    private static final String DELETE_COMMENT_BY_COMMENT_ID = "DELETE FROM comments WHERE comment_id=?";
    
    /** The Constant DELETE_NOTIFICATION_BY_NOTIFICATION_ID. */
    private static final String DELETE_NOTIFICATION_BY_NOTIFICATION_ID = "DELETE FROM notifications WHERE notification_id=?";
    
    /** The Constant DELETE_RATE_BY_NOTIFICATION_ID. */
    private static final String DELETE_RATE_BY_NOTIFICATION_ID = "DELETE FROM rates WHERE notification_id=?";
    
    /** The Constant DELETE_COMMENT_BY_NOTIFICATION_ID. */
    private static final String DELETE_COMMENT_BY_NOTIFICATION_ID = "DELETE FROM comments WHERE notification_id=?";
    
    /** The Constant CHANGE_NOTIFICATION_MESSAGE. */
    private static final String CHANGE_NOTIFICATION_MESSAGE = "UPDATE notifications SET message=? WHERE notification_id=?";

    /** The Constant ADD_NOTIFICATION. */
    private static final String ADD_NOTIFICATION = "INSERT INTO notifications VALUES(null, ?, ?, ?, ?, ?, ?)";
    
    /** The Constant ADD_NOTIFICATION_SET_DEFAULT_RATE. */
    private static final String ADD_NOTIFICATION_SET_DEFAULT_RATE = "INSERT INTO rates VALUES(?, ?, ?)";
    
    /** The Constant DEFAULT_MARK. */
    private static final int DEFAULT_MARK = 5;

    /** The Constant SEARCH_NOTIFICATION_BY_UNIT_NUMBER. */
    private static final String SEARCH_NOTIFICATION_BY_UNIT_NUMBER =
            "SELECT COUNT(n.notification_id) numberNotifications FROM notifications n " +
                    "LEFT JOIN units u ON n.unit_id=u.unit_id " +
                "WHERE u.unit_id=?";
    
    /** The Constant SEARCH_NOTIFICATION_BY_UNIT_COUNT_VALUE. */
    private static final String SEARCH_NOTIFICATION_BY_UNIT_COUNT_VALUE = "numberNotifications";
    
    /** The Constant SEARCH_NOTIFICATION_BY_UNIT. */
    private static final String SEARCH_NOTIFICATION_BY_UNIT =
            "SELECT n.notification_id, n.message, un.name, n.price, u.user_id, n.date, n.content, AVG(r.mark) mark FROM notifications n " +
                    "LEFT JOIN units un ON n.unit_id=un.unit_id " +
                    "LEFT JOIN users u ON n.user_id=u.user_id " +
                    "LEFT JOIN rates r ON n.notification_id=r.notification_id " +
                "WHERE un.unit_id=? " +
                "GROUP BY r.notification_id " +
                "ORDER BY n.date DESC " +
                "LIMIT ?, ?";

    /** The Constant ADD_COMMENT. */
    private static final String ADD_COMMENT = "INSERT INTO comments VALUES(null, ?, ?, ?, ?)";

    /** The Constant CHECK_IS_MARK_EXIST. */
    private static final String CHECK_IS_MARK_EXIST = "SELECT mark FROM rates WHERE user_id=? AND notification_id=?";
    
    /** The Constant INSERT_MARK. */
    private static final String INSERT_MARK = "INSERT INTO rates VALUES(?, ?, ?)";
    
    /** The Constant UPDATE_MARK. */
    private static final String UPDATE_MARK = "UPDATE rates SET mark=? WHERE user_id=? AND notification_id=?";
    
    /** The Constant SEARCH_RATE. */
    private static final String SEARCH_RATE =
            "SELECT AVG(mark) rate FROM rates " +
                "WHERE notification_id=? " +
                "GROUP BY notification_id";
    
    /** The Constant RATE. */
    private static final String RATE = "rate";

    /** The Constant SEARCH_ALL_NOTIFICATIONS_BY_AUTHOR_ID. */
    private static final String SEARCH_ALL_NOTIFICATIONS_BY_AUTHOR_ID =
            "SELECT n.notification_id, n.message, un.name, n.price, u.user_id, n.date, n.content, AVG(r.mark) mark FROM notifications n " +
                    "LEFT JOIN units un ON n.unit_id=un.unit_id " +
                    "LEFT JOIN users u ON n.user_id=u.user_id " +
                    "LEFT JOIN rates r ON n.notification_id=r.notification_id " +
                        "WHERE u.user_id=? " +
                    "GROUP BY r.notification_id " +
                    "ORDER BY n.date DESC " +
                    "LIMIT 50";

    /**
     * Search by id.
     *
     * @param id the id
     * @return the optional
     */
    @Override
    public Optional<Notification> searchById(long id) {
        return Optional.empty();
    }

    /**
     * Put mark.
     *
     * @param mark the mark
     * @param senderId the sender id
     * @param notificationId the notification id
     * @return the double
     * @throws PufarDAOException the pufar DAO exception
     */
    // mark
    @Override
    public double putMark(int mark, long senderId, long notificationId) throws PufarDAOException{

        PreparedStatement checkStatement = null;
        PreparedStatement updateStatement = null;
        PreparedStatement insertStatement = null;
        PreparedStatement findNewRateStatement = null;

        ResultSet checkResultSet = null;
        ResultSet findRateResultSet = null;

        Connection connection = null;
        try{
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);

            checkStatement = connection.prepareStatement(CHECK_IS_MARK_EXIST);
            checkStatement.setLong(1, senderId);
            checkStatement.setLong(2, notificationId);
            checkResultSet = checkStatement.executeQuery();

            if(checkResultSet.next()){
                updateStatement = connection.prepareStatement(UPDATE_MARK);
                updateStatement.setInt(1, mark);
                updateStatement.setLong(2, senderId);
                updateStatement.setLong(3, notificationId);

                updateStatement.executeUpdate();

            }
            else {
                insertStatement = connection.prepareStatement(INSERT_MARK);
                insertStatement.setLong(1, notificationId);
                insertStatement.setLong(2, senderId);
                insertStatement.setInt(3, mark);

                insertStatement.executeUpdate();

            }


            findNewRateStatement = connection.prepareStatement(SEARCH_RATE);
            findNewRateStatement.setLong(1, notificationId);
            findRateResultSet = findNewRateStatement.executeQuery();

            findRateResultSet.next();
            double rate = findRateResultSet.getDouble(RATE);

            connection.commit();

            return rate;
        }
        catch (SQLException e) {
            try { connection.rollback(); } catch (SQLException e1) { LOGGER.error(PufarDaoConstant.ROLLBACK_LOG); }
            throw new PufarDAOException("Mark wasn't added", e);
        }
        finally {
            try { DbUtils.close(checkResultSet); } catch (SQLException e) { LOGGER.error(PufarDaoConstant.CLOSE_RESULTSET_ERROR_LOG); }
            try { DbUtils.close(findRateResultSet); } catch (SQLException e) { LOGGER.error(PufarDaoConstant.CLOSE_RESULTSET_ERROR_LOG); }

            try { DbUtils.close(checkStatement); } catch (SQLException e) { LOGGER.error(PufarDaoConstant.CLOSE_RESULTSET_ERROR_LOG); }
            try { DbUtils.close(updateStatement); } catch (SQLException e) { LOGGER.error(PufarDaoConstant.CLOSE_RESULTSET_ERROR_LOG); }
            try { DbUtils.close(insertStatement); } catch (SQLException e) { LOGGER.error(PufarDaoConstant.CLOSE_RESULTSET_ERROR_LOG); }
            try { DbUtils.close(findNewRateStatement); } catch (SQLException e) { LOGGER.error(PufarDaoConstant.CLOSE_RESULTSET_ERROR_LOG); }

            try { DbUtils.close(connection); } catch (SQLException e) { LOGGER.error(PufarDaoConstant.CLOSE_CONNECTION_LOG); }
        }

    }
    
    /**
     * Search comment by notification id.
     *
     * @param notificationId the notification id
     * @return the list
     */
    // comments
    @Override
    public List<NotificationComment> searchCommentByNotificationId(long notificationId){

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try(Connection connection = ConnectionPool.getInstance().getConnection()){
            preparedStatement = connection.prepareStatement(SEARCH_COMMENTS_BY_NOTIFICATION_ID);
            preparedStatement.setLong(1, notificationId);
            resultSet = preparedStatement.executeQuery();

            return CommentMapper.mapComments(resultSet);
        }
        catch (SQLException e) {
            return new ArrayList<>();
        }
        finally {
            try { DbUtils.close(resultSet); } catch (SQLException e) { LOGGER.error(PufarDaoConstant.CLOSE_RESULTSET_ERROR_LOG); }
            try { DbUtils.close(preparedStatement); } catch (SQLException e) { LOGGER.error(PufarDaoConstant.CLOSE_STATEMENT_ERROR_LOG); }
        }

    }
    
    /**
     * Drop comment by id.
     *
     * @param commentId the comment id
     * @return true, if successful
     */
    @Override
    public boolean dropCommentById(long commentId) {
        boolean result;

        PreparedStatement preparedStatement = null;
        try(Connection connection = ConnectionPool.getInstance().getConnection()){
            preparedStatement = connection.prepareStatement(DELETE_COMMENT_BY_COMMENT_ID);
            preparedStatement.setLong(1, commentId);
            preparedStatement.executeUpdate();


            result = true;
        }
        catch (SQLException e) {
            result = false;
        }
        finally {
            try { DbUtils.close(preparedStatement); } catch (SQLException e) { LOGGER.error(PufarDaoConstant.CLOSE_STATEMENT_ERROR_LOG); }
        }

        return result;
    }
    
    /**
     * Adds the comment.
     *
     * @param comment the comment
     * @param senderId the sender id
     * @param notificationId the notification id
     * @return the long
     * @throws PufarDAOException the pufar DAO exception
     */
    @Override
    public long addComment(String comment, long senderId, long notificationId) throws PufarDAOException{
        long date = System.currentTimeMillis();

        PreparedStatement preparedStatement = null;
        try(Connection connection = ConnectionPool.getInstance().getConnection()){
            preparedStatement = connection.prepareStatement(ADD_COMMENT);
            preparedStatement.setLong(1, notificationId);
            preparedStatement.setLong(2, senderId);
            preparedStatement.setString(3, comment);

            preparedStatement.setLong(4, date);

            preparedStatement.executeUpdate();

        }
        catch (SQLException e) {
            throw new PufarDAOException("Comment wasn't added", e);
        }
        finally {
            try { DbUtils.close(preparedStatement); } catch (SQLException e) { LOGGER.error(PufarDaoConstant.CLOSE_STATEMENT_ERROR_LOG); }
        }

        return date;
    }

    /**
     * Search top notifications with limit.
     *
     * @param limit the limit
     * @return the list
     */
    @Override
    public List<Notification> searchTopNotificationsWithLimit(int limit){

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try(Connection connection = ConnectionPool.getInstance().getConnection()){
            preparedStatement = connection.prepareStatement(SEARCH_TOP_NOTIFICATIONS_SQL);
            preparedStatement.setLong(1, limit);
            resultSet = preparedStatement.executeQuery();

            List<Notification> notifications = NotificationMapper.mapNotification(resultSet);
            for(Notification notification : notifications){
                List<NotificationComment> notificationComments = searchCommentByNotificationId(notification.getNotificationId());
                notification.setComments(notificationComments);
            }

            return notifications;
        }
        catch (SQLException e) {
            return new ArrayList<>();
        }
        finally {
            try { DbUtils.close(resultSet); } catch (SQLException e) { LOGGER.error(PufarDaoConstant.CLOSE_RESULTSET_ERROR_LOG); }
            try { DbUtils.close(preparedStatement); } catch (SQLException e) { LOGGER.error(PufarDaoConstant.CLOSE_STATEMENT_ERROR_LOG); }
        }

    }

    /**
     * Search by parameters.
     *
     * @param parameters the parameters
     * @return the list
     */
    @Override
    public List<Notification> searchByParameters(NotificationParameter parameters) {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try(Connection connection = ConnectionPool.getInstance().getConnection()){
            String parametersSql = generateSearchWithParametersSql(parameters);

            preparedStatement = connection.prepareStatement(parametersSql);
            fullFillPreparedStatement(preparedStatement, parameters);

            resultSet = preparedStatement.executeQuery();

            List<Notification> notifications = NotificationMapper.mapNotification(resultSet);
            for(Notification notification : notifications){
                List<NotificationComment> notificationComments = searchCommentByNotificationId(notification.getNotificationId());
                notification.setComments(notificationComments);
            }

            return notifications;
        }
        catch (SQLException e) {
            return new ArrayList<>();
        }
        finally {
            try { DbUtils.close(resultSet); } catch (SQLException e) { LOGGER.error(PufarDaoConstant.CLOSE_RESULTSET_ERROR_LOG); }
            try { DbUtils.close(preparedStatement); } catch (SQLException e) { LOGGER.error(PufarDaoConstant.CLOSE_STATEMENT_ERROR_LOG); }
        }

    }
    
    /**
     * Generate search with parameters sql.
     *
     * @param parameters the parameters
     * @return the string
     */
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

        if(!whereConditions.isEmpty()){
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
    
    /**
     * Full fill prepared statement.
     *
     * @param preparedStatement the prepared statement
     * @param parameters the parameters
     * @throws SQLException the SQL exception
     */
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

    /**
     * Drop notification by id.
     *
     * @param notificationId the notification id
     * @return true, if successful
     */
    @Override
    public boolean dropNotificationById(long notificationId) {
        boolean result;

        PreparedStatement dropComments = null;
        PreparedStatement dropRates = null;
        PreparedStatement dropNotification = null;

        Connection connection = null;
        try{
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);

            dropComments = connection.prepareStatement(DELETE_RATE_BY_NOTIFICATION_ID);
            dropComments.setLong(1, notificationId);
            dropComments.executeUpdate();

            dropRates = connection.prepareStatement(DELETE_COMMENT_BY_NOTIFICATION_ID);
            dropRates.setLong(1, notificationId);
            dropRates.executeUpdate();

            // last, case foreign keys
            dropNotification = connection.prepareStatement(DELETE_NOTIFICATION_BY_NOTIFICATION_ID);
            dropNotification.setLong(1, notificationId);
            dropNotification.executeUpdate();

            connection.commit();
            result = true;
        }
        catch (SQLException e) {
            try { connection.rollback(); } catch (SQLException e1) { LOGGER.error(PufarDaoConstant.ROLLBACK_LOG); }
            LOGGER.warn("Notification wasn't deleted");
            result = false;
        }
        finally {
            try { DbUtils.close(dropComments); } catch (SQLException e) { LOGGER.error(PufarDaoConstant.CLOSE_RESULTSET_ERROR_LOG); }
            try { DbUtils.close(dropRates); } catch (SQLException e) { LOGGER.error(PufarDaoConstant.CLOSE_RESULTSET_ERROR_LOG); }
            try { DbUtils.close(dropNotification); } catch (SQLException e) { LOGGER.error(PufarDaoConstant.CLOSE_RESULTSET_ERROR_LOG); }

            try { DbUtils.close(connection); } catch (SQLException e) { LOGGER.error(PufarDaoConstant.CLOSE_CONNECTION_LOG); }
        }

        return result;
    }
    
    /**
     * Change notification message.
     *
     * @param notificationId the notification id
     * @param newMessage the new message
     * @return true, if successful
     */
    @Override
    public boolean changeNotificationMessage(long notificationId, String newMessage) {
        boolean result;

        PreparedStatement preparedStatement = null;
        try(Connection connection = ConnectionPool.getInstance().getConnection()){

            preparedStatement = connection.prepareStatement(CHANGE_NOTIFICATION_MESSAGE);
            preparedStatement.setString(1, newMessage);
            preparedStatement.setLong(2, notificationId);
            preparedStatement.executeUpdate();

            result = true;
        }
        catch (SQLException e) {
            result = false;
        }
        finally {
            try { DbUtils.close(preparedStatement); } catch (SQLException e) { LOGGER.error(PufarDaoConstant.CLOSE_STATEMENT_ERROR_LOG); }
        }

        return result;
    }
    
    /**
     * Adds the notification.
     *
     * @param notification the notification
     * @throws PufarDAOException the pufar DAO exception
     */
    @Override
    public void addNotification(Notification notification) throws PufarDAOException {

        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatementRate = null;
        ResultSet generatedKeys = null;

        Connection connection = null;
        try{
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);

            preparedStatement = connection.prepareStatement(ADD_NOTIFICATION, Statement.RETURN_GENERATED_KEYS);
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
            generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            long lastId =  generatedKeys.getLong(1);

            // Insert default mark
            preparedStatementRate = connection.prepareStatement(ADD_NOTIFICATION_SET_DEFAULT_RATE);
            preparedStatementRate.setLong(1,lastId);
            preparedStatementRate.setLong(2, CommonConstant.SYSTEM_USER_ID);
            preparedStatementRate.setInt(3, DEFAULT_MARK);
            preparedStatementRate.executeUpdate();

            connection.commit();
        }
        catch (SQLException e) {
            try { connection.rollback(); } catch (SQLException e1) { LOGGER.error(PufarDaoConstant.ROLLBACK_LOG); }
            throw new PufarDAOException("Notification was'n added", e);
        }
        catch (IOException e) {
            throw new PufarDAOException("Notification was'n added, case image wasn't convert to BLOB", e);
        }
        finally {
            try { DbUtils.close(generatedKeys); } catch (SQLException e) { LOGGER.error(PufarDaoConstant.CLOSE_RESULTSET_ERROR_LOG); }
            try { DbUtils.close(preparedStatementRate); } catch (SQLException e) { LOGGER.error(PufarDaoConstant.CLOSE_RESULTSET_ERROR_LOG); }
            try { DbUtils.close(preparedStatement); } catch (SQLException e) { LOGGER.error(PufarDaoConstant.CLOSE_RESULTSET_ERROR_LOG); }

            try { DbUtils.close(connection); } catch (SQLException e) { LOGGER.error(PufarDaoConstant.CLOSE_CONNECTION_LOG); }
        }

    }
    
    /**
     * Search notifications by unit number.
     *
     * @param unitType the unit type
     * @return the long
     */
    @Override
    public long searchNotificationsByUnitNumber(UnitType unitType){
        long result;

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try(Connection connection = ConnectionPool.getInstance().getConnection()){

            preparedStatement = connection.prepareStatement(SEARCH_NOTIFICATION_BY_UNIT_NUMBER);
            preparedStatement.setInt(1, UnitType.getUnitDBPosition(unitType));

            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            result = resultSet.getInt(SEARCH_NOTIFICATION_BY_UNIT_COUNT_VALUE);

        }
        catch (SQLException e) {
            result = 0;
        }
        finally {
            try { DbUtils.close(resultSet); } catch (SQLException e) { LOGGER.error(PufarDaoConstant.CLOSE_RESULTSET_ERROR_LOG); }
            try { DbUtils.close(preparedStatement); } catch (SQLException e) { LOGGER.error(PufarDaoConstant.CLOSE_STATEMENT_ERROR_LOG); }
        }

        return result;
    }
    
    /**
     * Search notifications by unit.
     *
     * @param unitType the unit type
     * @param limitStart the limit start
     * @param limitStep the limit step
     * @return the list
     */
    @Override
    public List<Notification> searchNotificationsByUnit(UnitType unitType, int limitStart, int limitStep){
        List<Notification> result;

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try(Connection connection = ConnectionPool.getInstance().getConnection()){
            preparedStatement = connection.prepareStatement(SEARCH_NOTIFICATION_BY_UNIT);
            preparedStatement.setInt(1, UnitType.getUnitDBPosition(unitType));
            preparedStatement.setInt(2, limitStart);
            preparedStatement.setInt(3, limitStep);

            resultSet = preparedStatement.executeQuery();
            result = NotificationMapper.mapNotification(resultSet);

            for(Notification notification : result){
                notification.setComments(searchCommentByNotificationId(notification.getNotificationId()));
            }

        }
        catch (SQLException e) {
            result = new ArrayList<>();
        }
        finally {
            try { DbUtils.close(resultSet); } catch (SQLException e) { LOGGER.error(PufarDaoConstant.CLOSE_RESULTSET_ERROR_LOG); }
            try { DbUtils.close(preparedStatement); } catch (SQLException e) { LOGGER.error(PufarDaoConstant.CLOSE_STATEMENT_ERROR_LOG); }
        }
        return result;
    }
    
    /**
     * Search all notifications by author id.
     *
     * @param authorIdw the author idw
     * @return the list
     */
    @Override
    public List<Notification> searchAllNotificationsByAuthorId(long authorIdw){

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try(Connection connection = ConnectionPool.getInstance().getConnection()){
            preparedStatement = connection.prepareStatement(SEARCH_ALL_NOTIFICATIONS_BY_AUTHOR_ID, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, authorIdw);
            resultSet = preparedStatement.executeQuery();

            return NotificationMapper.mapNotification(resultSet);
        }
        catch (SQLException e) {
            return new ArrayList<>();
        }
        finally {
            try { DbUtils.close(resultSet); } catch (SQLException e) { LOGGER.error(PufarDaoConstant.CLOSE_RESULTSET_ERROR_LOG); }
            try { DbUtils.close(preparedStatement); } catch (SQLException e) { LOGGER.error(PufarDaoConstant.CLOSE_STATEMENT_ERROR_LOG); }
        }

    }

}
