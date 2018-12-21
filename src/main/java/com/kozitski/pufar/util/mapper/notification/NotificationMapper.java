package com.kozitski.pufar.util.mapper.notification;

import com.kozitski.pufar.command.impl.autorization.LoginCommand;
import com.kozitski.pufar.entity.notification.Notification;
import com.kozitski.pufar.entity.notification.UnitType;
import com.kozitski.pufar.util.CommonConstant;
import com.kozitski.pufar.util.path.WebPathReturner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class NotificationMapper  {
    private static Logger LOGGER = LoggerFactory.getLogger(NotificationMapper.class);


    private static final String NOTIFICATION_ID = "n.notification_id";
    private static final String NOTIFICATION_MESSAGE = "n.message";
    private static final String NOTIFICATION_UNIT = "un.name";
    private static final String NOTIFICATION_PRICE = "n.price";
    private static final String NOTIFICATION_SENDER_ID = "u.user_id";
    private static final String NOTIFICATION_DATE = "n.date";
    private static final String NOTIFICATION_MARK = "mark";
    private static final String NOTIFICATION_CONTENT = "n.content";

    public static ArrayList<Notification> mapNotification(ResultSet resultSet) throws SQLException {
        ArrayList<Notification> result = new ArrayList<>();

        while (resultSet.next()){
            Notification notification = new Notification();

            notification.setNotificationId(resultSet.getLong(NOTIFICATION_ID));
            notification.setMessage(resultSet.getString(NOTIFICATION_MESSAGE));
            notification.setUnit(UnitType.valueOf(resultSet.getString(NOTIFICATION_UNIT).toUpperCase()));
            notification.setPrice(resultSet.getDouble(NOTIFICATION_PRICE));

            notification.setUserId(resultSet.getLong(NOTIFICATION_SENDER_ID));
            notification.setDate(new Date(resultSet.getLong(NOTIFICATION_DATE)));
            notification.setRate(resultSet.getDouble(NOTIFICATION_MARK));

            try {
                Blob blob = resultSet.getBlob(NOTIFICATION_CONTENT);
                InputStream inputStream = blob.getBinaryStream();

                BufferedImage image = ImageIO.read(inputStream);
                notification.setImage(image);
            }
            catch (IOException | SQLException | NullPointerException e) {
                LOGGER.info("Notification image was downloaded how default", e);

                File file = new File(WebPathReturner.webPath + CommonConstant.DEFAULT_IMAGE_PATH);
                try {
                    BufferedImage image = ImageIO.read(file);
                    notification.setImage(image);
                }
                catch (IOException e1) {
                    LOGGER.warn("Notification image was not downloaded", e);
                }
            }

            result.add(notification);
        }

        return result;
    }

}
