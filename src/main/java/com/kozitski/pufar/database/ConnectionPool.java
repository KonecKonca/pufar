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

public class ConnectionPool {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionPool.class);
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/pufar?serverTimezone=UTC&useSSL=false";
    private static final String PROPERTY_PATH = "/WEB-INF/classes/pool/connectionPool.properties";

    private static String fullPath = (WebPathReturner.webPath + PROPERTY_PATH);

    private static final int INITIAL_CAPACITY = 15;

    private ArrayBlockingQueue<Connection> freeConnections = new ArrayBlockingQueue<>(INITIAL_CAPACITY);
    private ArrayBlockingQueue<Connection> releaseConnections = new ArrayBlockingQueue<>(INITIAL_CAPACITY);

    private static ReentrantLock lock = new ReentrantLock();
    private static ConnectionPool connectionPool;

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
    void releaseConnection(Connection connection) {

        releaseConnections.remove(connection);
        freeConnections.offer(connection);

    }

    public static void setFullPath(String fullPath) {
        ConnectionPool.fullPath = fullPath;
    }
    public String tracePollCapacity(){
        return "freeConnections: " + freeConnections.size() + " releaseConnections: " + releaseConnections.size();
    }
    public int getFreeConnectionsSize(){
        return freeConnections.size();
    }
    public int getReleaseConnectionsSize(){
        return releaseConnections.size();
    }

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
