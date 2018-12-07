package com.kozitski.pufar.connection;

import com.kozitski.pufar.exception.PufarDAOException;
import com.kozitski.pufar.util.path.WebPathReturner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysql.jdbc.Driver;
import org.slf4j.Marker;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Enumeration;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class PoolConnection {
    private static Logger LOGGER = LoggerFactory.getLogger(PoolConnection.class);
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/pufar?serverTimezone=UTC&useSSL=false";
    private static final String PROPERTY_PATH = "/WEB-INF/classes/poll/poolConnection.properties";
    private Properties properties;

    private static final int MIN_POOL_CAPACITY = 5;
    private static final int MAX_POOL_CAPACITY = 20;
    private static final int INITIAL_CAPACITY = 10;

    private ArrayBlockingQueue<Connection> freeConnections = new ArrayBlockingQueue<>(MAX_POOL_CAPACITY);
    private ArrayBlockingQueue<Connection> releaseConnections = new ArrayBlockingQueue<>(MAX_POOL_CAPACITY);

    private static ReentrantLock lock = new ReentrantLock();
    private static PoolConnection poolConnection;
    public static PoolConnection getInstance(){
        if(poolConnection != null){
            return poolConnection;
        }

        try {
            lock.lock();
            if(poolConnection == null){
                try {
                    poolConnection = new PoolConnection();
                } catch (SQLException e) {
                    LOGGER.error("Can not get Instance", e);
                    throw new RuntimeException("Can not get Instance", e);
                }
            }
        }
        finally {
            lock.unlock();
        }

        return poolConnection;
    }

    private PoolConnection() throws SQLException {
        DriverManager.registerDriver(new Driver());

        init();
    }
    private void init() {

        String fullPath = (WebPathReturner.webPath + PROPERTY_PATH);
        properties = new Properties();

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
            lock.lock();

            Connection connection;

            if(freeConnections.size() < MIN_POOL_CAPACITY && (freeConnections.size() + releaseConnections.size()) < MAX_POOL_CAPACITY){
                connection = new ProxyConnection(DriverManager.getConnection(CONNECTION_URL, properties));
                releaseConnections.offer(connection);
            }
            else {
                connection = freeConnections.take();
            }

            return connection;
        }
        catch (InterruptedException | SQLException e) {
            throw new RuntimeException("Can not get connection", e);
        }
        finally {
            lock.unlock();
        }

    }
    void releaseConnection(Connection connection) {

        try {
            lock.lock();

            if(freeConnections.size() > MIN_POOL_CAPACITY + INITIAL_CAPACITY){
                ((ProxyConnection)connection).realClose();
                for (int i = 0; i < MIN_POOL_CAPACITY - 1; i++) {
                    ((ProxyConnection) freeConnections.take()).realClose();
                }
            }
            else {
                freeConnections.offer(connection);
            }
        }
        catch (InterruptedException e) {
            LOGGER.error("connection take fell down", e);
            throw new RuntimeException("connection take fell down", e);
        }
        finally {
            lock.unlock();
        }

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

        poolConnection = null;
    }

}
