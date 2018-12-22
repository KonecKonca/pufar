package com.kozitski.pufar.controller.image;

import com.kozitski.pufar.connection.PoolConnection;
import com.kozitski.pufar.entity.notification.Notification;
import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.exception.PufarDAOException;
import com.kozitski.pufar.util.CommonConstant;
import com.kozitski.pufar.util.mapper.user.UserMapper;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

@WebServlet("/blob")
public class LoadBlobController extends HttpServlet {
    private static final String LOAD_BLOB = "update notifications set content=? WHERE unit_id!=2";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try(Connection connection = PoolConnection.getInstance().getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(LOAD_BLOB);

            File file = new File("D:/notificationDefault.png");
            FileInputStream fileInputStream = new FileInputStream(file);

            preparedStatement.setBinaryStream(1, fileInputStream);

            preparedStatement.executeUpdate();
        }

        catch (SQLException e) {

        }

    }

}
