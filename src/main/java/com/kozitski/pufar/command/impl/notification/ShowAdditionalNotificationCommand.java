package com.kozitski.pufar.command.impl.notification;

import com.kozitski.pufar.command.AbstractCommand;
import com.kozitski.pufar.command.PagePath;
import com.kozitski.pufar.command.RequestValue;
import com.kozitski.pufar.command.Router;
import com.kozitski.pufar.entity.comment.NotificationComment;
import com.kozitski.pufar.entity.nomber.MobilPhoneNumber;
import com.kozitski.pufar.entity.notification.Notification;
import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.entity.user.UserStatus;
import com.kozitski.pufar.service.notification.NotificationService;
import com.kozitski.pufar.service.notification.NotificationServiceImpl;
import com.kozitski.pufar.util.CommonConstant;

import java.util.ArrayList;
import java.util.Arrays;

public class ShowAdditionalNotificationCommand extends AbstractCommand {
    private static final String GOT_NOTIFICATION = "notification";

    private static final String OWNER_USER = "ownerUser";
    private static final String LOOKING_NOTIFICATION = "lookingNotification";

    private NotificationService notificationService = new NotificationServiceImpl();

    @Override
    @SuppressWarnings("unchecked")
    public Router execute(RequestValue request) {
        Router router = new Router();
        router.setPagePath(PagePath.NOTIFICATION_ADDITIONAL.getJspPath());

        // getting from template real Notification
        long notificationId = Long.parseLong((String) request.getAttribute(GOT_NOTIFICATION));
        Notification notification = null;
        for(Notification notif : (ArrayList<Notification>) request.getAttribute(CommonConstant.CURRENT_NOTIFICATIONS)){
            if(notif.getNotificationId() == notificationId){
                notification = notif;
            }
        }

        // push to session fake user and notification for visual test
        request.servletSessionPut(OWNER_USER, new User(23, "Alex", "password", UserStatus.SIMPLE_USER,
                false,   new MobilPhoneNumber(2233, String.valueOf(375), String.valueOf(25), String.valueOf(9550317))));
        Notification testNotification = ((ArrayList<Notification>) request.getAttribute(CommonConstant.CURRENT_NOTIFICATIONS)).get(0);
        testNotification.setComments(new ArrayList<NotificationComment>(
                Arrays.asList(new NotificationComment(1, "user1", "comment1"), new NotificationComment(2, "user2", "comment2"))));
        request.servletSessionPut(LOOKING_NOTIFICATION, testNotification);

        return router;
    }

}
