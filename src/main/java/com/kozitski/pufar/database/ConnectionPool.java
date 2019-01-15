package com.kozitski.pufar.database;

import com.kozitski.pufar.util.path.WebPathReturner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mysql.jdbc.Driver;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Enumeration;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

// TODO: Auto-generated Javadoc
/**
 * The Class ConnectionPool.
 */
public class ConnectionPool {
    
    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionPool.class);
    
    /** The Constant CONNECTION_URL. */
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/pufar?serverTimezone=UTC&useSSL=false";
    
    /** The Constant PROPERTY_PATH. */
    private static final String PROPERTY_PATH = "/WEB-INF/classes/pool/connectionPool.properties";

    /** The full path. */
    private static String fullPath = (WebPathReturner.webPath + PROPERTY_PATH);

    /** The Constant INITIAL_CAPACITY. */
    private static final int INITIAL_CAPACITY = 15;

    /** The free connections. */
    private ArrayBlockingQueue<Connection> freeConnections = new ArrayBlockingQueue<>(INITIAL_CAPACITY);
    
    /** The release connections. */
    private ArrayBlockingQueue<Connection> releaseConnections = new ArrayBlockingQueue<>(INITIAL_CAPACITY);

    /** The lock. */
    private static ReentrantLock lock = new ReentrantLock();
    
    /** The connection pool. */
    private static ConnectionPool connectionPool;

    /**
     * Gets the single instance of ConnectionPool.
     *
     * @return single instance of ConnectionPool
     */
    public static ConnectionPool getInstance(){

        if(connectionPool == null){
            try {
                lock.lock();
                if(connectionPool == null){
                    connectionPool = new ConnectionPool();
                }
            }
            catch (SQLException e) {
                LOGGER.error("Can not get Instance", e);
                throw new RuntimeException("Can not get Instance", e);
            } finally {
                lock.unlock();
            }
        }

        return connectionPool;
    }

    /**
     * Instantiates a new connection pool.
     *
     * @throws SQLException the SQL exception
     */
    private ConnectionPool() throws SQLException {

        try {
            lock.lock();

            if(connectionPool != null){
                throw new UnsupportedOperationException();
            }
            else {
                DriverManager.registerDriver(new Driver());
                init();
            }
        }
        finally {
            lock.unlock();
        }

    }
    
    /**
     * Inits the.
     */
    private void init() {

        Properties properties = new Properties();

        try(FileInputStream fileInputStream = new FileInputStream(fullPath)) {
            properties.load(fileInputStream);
        }
        catch (IOException e) {
            LOGGER.error("Error while reading properties", e);
        }

        for (int i = 0; i < INITIAL_CAPACITY; i++) {
            try {
                Connection connection = new ProxyConnection(DriverManager.getConnection(CONNECTION_URL, properties));
                freeConnections.add(connection);
            }
            catch (SQLException e) {
                LOGGER.error("Pool can not initialize", e);
                throw new RuntimeException("Pool can not initialize", e);
            }
        }

    }

    /**
     * Gets the connection.
     *
     * @return the connection
     */
    public Connection getConnection() {
        try {
            Connection connection = freeConnections.take();
            releaseConnections.offer(connection);

            return connection;
        }
        catch (InterruptedException  e) {
            throw new RuntimeException("Can not get database", e);
        }

    }
    
    /**
     * Release connection.
     *
     * @param connection the connection
     */
    void releaseConnection(Connection connection) {

        releaseConnections.remove(connection);
        freeConnections.offer(connection);

    }

    /**
     * Sets the full path.
     *
     * @param fullPath the new full path
     */
    public static void setFullPath(String fullPath) {
        ConnectionPool.fullPath = fullPath;
    }
    
    /**
     * Trace poll capacity.
     *
     * @return the string
     */
    public String tracePollCapacity(){
        return "freeConnections: " + freeConnections.size() + " releaseConnections: " + releaseConnections.size();
    }
    
    /**
     * Gets the free connections size.
     *
     * @return the free connections size
     */
    public int getFreeConnectionsSize(){
        return freeConnections.size();
    }
    
    /**
     * Gets the release connections size.
     *
     * @return the release connections size
     */
    public int getReleaseConnectionsSize(){
        return releaseConnections.size();
    }

    /**
     * Destroy.
     */
    public void destroy(){

        for (int i = 0; i < freeConnections.size(); i++) {
            try {
                ProxyConnection connection = (ProxyConnection) freeConnections.take();
                connection.realClose();
            }
            catch (InterruptedException e) {
                LOGGER.error("Connection close exception", e);
            }
        }

        try {
            Enumeration<java.sql.Driver> drivers = DriverManager.getDrivers();
            while (drivers.hasMoreElements()) {
                java.sql.Driver driver = drivers.nextElement();
                DriverManager.deregisterDriver(driver);

            }
        }
        catch (SQLException e) {
            LOGGER.error("Drivers were not deregistrated", e);
        }

    }

}
