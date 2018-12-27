package com.kozitski.pufar.command.impl.notification.additional;

import com.kozitski.pufar.command.AbstractCommand;
import com.kozitski.pufar.command.PagePath;
import com.kozitski.pufar.command.RequestValue;
import com.kozitski.pufar.command.Router;
import com.kozitski.pufar.entity.notification.Notification;
import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.service.user.UserService;
import com.kozitski.pufar.service.user.UserServiceImpl;
import com.kozitski.pufar.util.CommonConstant;

import java.util.ArrayList;
import java.util.Optional;

public class ShowAdditionalNotificationCommand extends AbstractCommand {
    private static final String GOT_NOTIFICATION = "notification";

    private static final String OWNER_USER = "ownerUser";
    private static final String LOOKING_NOTIFICATION = "lookingNotification";

    private UserService userService = new UserServiceImpl();

    @Override
    @SuppressWarnings("unchecked")
    public Router execute(RequestValue request) {
        Router router = new Router();
        router.setPagePath(PagePath.NOTIFICATION_ADDITIONAL.getJspPath());

        long notificationId = Long.parseLong((String) request.getAttribute(GOT_NOTIFICATION));

        Notification notification = null;
        for(Notification notif : (ArrayList<Notification>) request.getAttribute(CommonConstant.CURRENT_NOTIFICATIONS)){
            if(notif.getNotificationId() == notificationId){
                notification = notif;
            }
        }
        request.servletSessionPut(LOOKING_NOTIFICATION, notification);

        if(notification != null){
            long userOwnerId = notification.getUserId();
            Optional<User> user = userService.searchUserById(userOwnerId);
            User userOwner;
            if(user.isPresent()){
                userOwner = user.get();
                request.servletSessionPut(OWNER_USER, userOwner);
            }
        }

        return router;
    }

}
