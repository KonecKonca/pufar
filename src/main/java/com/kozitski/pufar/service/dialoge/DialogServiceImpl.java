package com.kozitski.pufar.service.dialoge;

import com.kozitski.pufar.command.RequestValue;
import com.kozitski.pufar.dao.dialoge.DialogDAO;
import com.kozitski.pufar.dao.dialoge.MySQLDialogDao;
import com.kozitski.pufar.entity.message.UserMessage;
import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.entity.user.Users;
import com.kozitski.pufar.exception.PufarDAOException;
import com.kozitski.pufar.util.CommonConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class DialogServiceImpl implements DialogService {
    private static Logger LOGGER = LoggerFactory.getLogger(DialogServiceImpl.class);

    private DialogDAO dialogDAO = new MySQLDialogDao();

    @Override
    public List<UserMessage> searchAllMessagesFromTo(long fromUserId, long toUserId) {
        return dialogDAO.searchAllMessagesFromTo(fromUserId, toUserId);
    }
    @Override
    public List<UserMessage> searchAllMessagesBetween(long userId1, long userId2) {
        return dialogDAO.searchAllMessagesBetween(userId1, userId2);
    }
    @Override
    public List<UserMessage> searchMessagesBetweenWithLimit(long userId1, long userId2, int since, int howMuch) {
        return dialogDAO.searchMessagesBetweenWithLimit(userId1, userId2, since, howMuch);
    }
    @Override
    public ArrayList<User> searchPopularUser(long forWhomUserId, int howMuch) {
        return dialogDAO.searchPopularUser(forWhomUserId, howMuch);
    }

    @Override
    public int numberOfMessagesBetween(long userId1, long userId2) {
        return searchAllMessagesBetween(userId1, userId2).size();
    }
    @Override
    public void addMessage(long senderId, long receiverId, String message) {
        try {
            dialogDAO.addMessage(senderId, receiverId, message);
        }
        catch (PufarDAOException e) {
            LOGGER.warn("Message was not added", e);
        }
    }

    @Override
    public void showDialogs(RequestValue requestValue){

        User currentUser = ((User)(requestValue.getAttribute(CommonConstant.CURRENT_USER)));
        long currentUserId = currentUser.getUserId();

        int msgStartNumber;
        int howMuchMessages = (int) requestValue.getAttribute(CommonConstant.HOW_MUCH_MESSAGES);

        List previousList = (List) requestValue.getAttribute(CommonConstant.TOP_USERS);
        if(previousList == null){
            List<User> users = Users.createUserArrayList(searchPopularUser(currentUserId, CommonConstant.HOW_MUCH_USERS_CHAT_PAGE));

            List<UserMessage> lastMessagesWithTopUser = null;
            if(users.size() > 0){
                User currentOpponent = users.get(0);
                requestValue.servletSessionPut(CommonConstant.CURRENT_OPPONENT, currentOpponent);

                msgStartNumber = numberOfMessagesBetween(currentUserId, currentOpponent.getUserId());
                requestValue.servletSessionPut(CommonConstant.LAST_MESSAGE, msgStartNumber);

                if(msgStartNumber - howMuchMessages < 0){
                    lastMessagesWithTopUser = searchMessagesBetweenWithLimit(currentUserId, currentOpponent.getUserId(), 0, howMuchMessages);
                }
                else {
                    lastMessagesWithTopUser = searchMessagesBetweenWithLimit(currentUserId, currentOpponent.getUserId(), msgStartNumber - howMuchMessages, howMuchMessages);
                }
            }

            requestValue.servletSessionPut(CommonConstant.TOP_USERS, users);
            requestValue.servletSessionPut(CommonConstant.LAST_MESSAGES, lastMessagesWithTopUser);
        }
        else {
            List<User> users = Users.createUserArrayList(searchPopularUser(currentUserId, CommonConstant.HOW_MUCH_USERS_CHAT_PAGE));

            List<UserMessage> lastMessagesWithTopUser;
            User currentOpponent = (User) requestValue.getAttribute(CommonConstant.CURRENT_OPPONENT);

            msgStartNumber = numberOfMessagesBetween(currentUserId, currentOpponent.getUserId());
            requestValue.servletSessionPut(CommonConstant.LAST_MESSAGE, msgStartNumber);

            if(msgStartNumber - howMuchMessages < 0){
                lastMessagesWithTopUser = searchMessagesBetweenWithLimit(currentUserId, currentOpponent.getUserId(), 0, howMuchMessages);
            }
            else {
                lastMessagesWithTopUser = searchMessagesBetweenWithLimit(currentUserId, currentOpponent.getUserId(), msgStartNumber - howMuchMessages, howMuchMessages);
            }

            requestValue.servletSessionPut(CommonConstant.TOP_USERS, users);
            requestValue.servletSessionPut(CommonConstant.LAST_MESSAGES, lastMessagesWithTopUser);
        }

    }
    @Override
    public void showNextDialogs(RequestValue requestValue){
        User currentUser = ((User)(requestValue.getAttribute(CommonConstant.CURRENT_USER)));
        long currentUserId = currentUser.getUserId();

        int msgStartNumber = (int)requestValue.getAttribute(CommonConstant.LAST_MESSAGE);

        List<User> users = Users.createUserArrayList(searchPopularUser(currentUserId, CommonConstant.HOW_MUCH_USERS_CHAT_PAGE));

        List<UserMessage> lastMessagesWithTopUser = null;
        if(users.size() > 0){
            User currentOpponent = (User) requestValue.getAttribute(CommonConstant.CURRENT_OPPONENT);

            int fullSize = numberOfMessagesBetween(currentUserId,currentOpponent.getUserId());
            int howMuchMessages = (int) requestValue.getAttribute(CommonConstant.HOW_MUCH_MESSAGES);

            msgStartNumber = msgStartNumber + howMuchMessages;
            if(msgStartNumber > fullSize){
                msgStartNumber = fullSize;
            }
            if(msgStartNumber - howMuchMessages < 0){
                msgStartNumber = howMuchMessages;
            }
            lastMessagesWithTopUser = searchMessagesBetweenWithLimit(currentUserId, currentOpponent.getUserId(), msgStartNumber - howMuchMessages, howMuchMessages);
            requestValue.servletSessionPut(CommonConstant.LAST_MESSAGE, msgStartNumber);

        }

        requestValue.servletSessionPut(CommonConstant.TOP_USERS, users);
        requestValue.servletSessionPut(CommonConstant.LAST_MESSAGES, lastMessagesWithTopUser);
    }
    @Override
    public void showPreviousDialogs(RequestValue requestValue){
        User currentUser = ((User)(requestValue.getAttribute(CommonConstant.CURRENT_USER)));
        long currentUserId = currentUser.getUserId();

        int msgStartNumber = (int)requestValue.getAttribute(CommonConstant.LAST_MESSAGE);

        List<User> users = Users.createUserArrayList(searchPopularUser(currentUserId, CommonConstant.HOW_MUCH_USERS_CHAT_PAGE));

        List<UserMessage> lastMessagesWithTopUser = null;
        if(users.size() > 0){
            User currentOpponent = (User) requestValue.getAttribute(CommonConstant.CURRENT_OPPONENT);

            int howMuchMessages = (int) requestValue.getAttribute(CommonConstant.HOW_MUCH_MESSAGES);
            msgStartNumber = msgStartNumber - howMuchMessages;
            if(msgStartNumber - howMuchMessages < 0){
                msgStartNumber = howMuchMessages;
            }

            lastMessagesWithTopUser = searchMessagesBetweenWithLimit(currentUserId, currentOpponent.getUserId(), msgStartNumber - howMuchMessages, howMuchMessages);
            requestValue.servletSessionPut(CommonConstant.LAST_MESSAGE, msgStartNumber);

        }

        requestValue.servletSessionPut(CommonConstant.TOP_USERS, users);
        requestValue.servletSessionPut(CommonConstant.LAST_MESSAGES, lastMessagesWithTopUser);
    }
    @Override
    public void chooseDialogWithUser(RequestValue requestValue){

     long chosenUserID = Long.parseLong(requestValue.getAttribute(CommonConstant.CHOSEN_USER).toString());

     User chosenUser = null;
     List topUsers = (List) requestValue.getAttribute(CommonConstant.TOP_USERS);

     for(Object o : topUsers){
         User user = (User) o;
         if(user.getUserId() == chosenUserID){
             chosenUser = user;
             break;
         }
     }

     requestValue.servletSessionPut(CommonConstant.CURRENT_OPPONENT, chosenUser);

     showDialogs(requestValue);

    }

}
