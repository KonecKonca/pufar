package com.kozitski.pufar.database;

import org.mockito.Mockito;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.testng.Assert.*;

public class ConnectionPoolTest {

    @BeforeClass
    public void initialize(){
        ConnectionPool.setFullPath("src/main/resources/pool/connectionPool.properties");
    }

    @Test
    public void connectionPoolInitTest(){
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        assertNotNull(connectionPool);
    }
    @Test
    public void connectionPoolGetInstanceTest(){
        ConnectionPool actual = ConnectionPool.getInstance();
        ConnectionPool expected = ConnectionPool.getInstance();

        assertEquals(actual, expected);
    }

    @Test
    public void connectionPoolFreeConnectionsNumberTest(){
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        Connection connection = connectionPool.getConnection();

        int actual = 14;
        int expected = connectionPool.getFreeConnectionsSize();

        try {
            connection.close();
        }
        catch (SQLException e) {/*NOP*/ }
        assertEquals(actual, expected);

    }
    @Test
    public void connectionPoolReleaseConnectionsNumberTest(){
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        Connection connection = connectionPool.getConnection();

        int actual = 1;
        int expected = connectionPool.getReleaseConnectionsSize();

        try {
            connection.close();
        }
        catch (SQLException e) {/*NOP*/ }
        assertEquals(actual, expected);

    }


}