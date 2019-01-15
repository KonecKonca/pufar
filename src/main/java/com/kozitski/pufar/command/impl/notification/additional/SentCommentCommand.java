package com.kozitski.pufar.command.impl.notification.additional;

import com.kozitski.pufar.command.*;
import com.kozitski.pufar.command.request.AbstractCommand;
import com.kozitski.pufar.entity.comment.NotificationComment;
import com.kozitski.pufar.entity.notification.Notification;
import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.exception.PufarServiceException;
import com.kozitski.pufar.exception.PufarValidationException;
import com.kozitski.pufar.service.notification.NotificationService;
import com.kozitski.pufar.util.CommonConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;

/**
 * The Class SentCommentCommand.
 * Command for sending comment
 */
public class SentCommentCommand extends AbstractCommand {
    
    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(SentCommentCommand.class);

    /** The Constant COMMENT_VALUE. */
    private static final String COMMENT_VALUE = "commentValue";
    
    /** The Constant NOTIFICATION_ID. */
    private static final String NOTIFICATION_ID = "notificationId";

    /** The notification service. */
    @InjectService
    private NotificationService notificationService;

    /**
     * Execute.
     *
     * @param request the request
     * @return the router
     */
    @Override
    @SuppressWarnings("unchecked")
    public Router execute(RequestValue request) {
        Router router = new Router();
        router.setRouteType(Router.RouteType.REDIRECT);
        router.setPagePath(PagePath.NOTIFICATION_ADDITIONAL.getJspPath());

        String comment = new String(((String) request.getAttribute(COMMENT_VALUE)).getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

        String notificationIdString = (String) request.getAttribute(NOTIFICATION_ID);
        long notificationId = Long.parseLong(notificationIdString);

        User currentUser = (User) request.getAttribute(CommonConstant.CURRENT_USER);
        long senderId = currentUser.getUserId();

        try {
            long date = notificationService.sentComment(comment, senderId, notificationId);


            Notification handledNotification = null;
            ArrayList<Notification> notifications = (ArrayList<Notification>) request.getAttribute(CommonConstant.CURRENT_NOTIFICATIONS);
            for (Notification notification : notifications) {
                if (notification.getNotificationId() == notificationId) {
                    handledNotification = notification;
                }
            }
            if (handledNotification != null) {
                NotificationComment notificationComment = new NotificationComment();
                notificationComment.setSenderLogin(currentUser.getLogin());
                notificationComment.setDate(new Date(date));
                notificationComment.setComment(comment);

                handledNotification.insertNewComment(notificationComment);
            }

        } catch (PufarServiceException e) {
            LOGGER.warn("comment wasn't added");
        } catch (PufarValidationException e) {
            LOGGER.warn("incorrect input data", e);
        }

        return router;
    }

}


