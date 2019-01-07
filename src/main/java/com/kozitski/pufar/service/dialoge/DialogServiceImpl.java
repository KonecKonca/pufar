package com.kozitski.pufar.service.dialoge;

import com.kozitski.pufar.command.RequestValue;
import com.kozitski.pufar.dao.dialoge.DialogDAO;
import com.kozitski.pufar.entity.message.UserMessage;
import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.exception.PufarDAOException;
import com.kozitski.pufar.exception.PufarValidationException;
import com.kozitski.pufar.service.AbstractService;
import com.kozitski.pufar.service.InjectDao;
import com.kozitski.pufar.util.CommonConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class DialogServiceImpl extends AbstractService implements DialogService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DialogServiceImpl.class);

    @InjectDao
    private DialogDAO dialogDAO;

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
    public List<User> searchPopularUser(long forWhomUserId, int howMuch) {
        return dialogDAO.searchPopularUser(forWhomUserId, howMuch);
    }

    @Override
    public int numberOfMessagesBetween(long userId1, long userId2) {
        return searchAllMessagesBetween(userId1, userId2).size();
    }

    @Override
    public void addMessage(long senderId, long receiverId, String message) throws PufarValidationException {
        try {
            dialogDAO.addMessage(senderId, receiverId, message);
        } catch (PufarDAOException e) {
            LOGGER.warn("Message was not added", e);
        }
    }

    @Override
    public void showDialogs(RequestValue requestValue) {
        User currentUser = ((User) (requestValue.getAttribute(CommonConstant.CURRENT_USER)));
        long currentUserId = currentUser.getUserId();

        int msgStartNumber;
        int howMuchMessages = (int) requestValue.getAttribute(CommonConstant.HOW_MUCH_MESSAGES);

        List<User> popularUsersList = searchPopularUser(currentUserId, CommonConstant.HOW_MUCH_USERS_CHAT_PAGE);

        List<UserMessage> lastMessagesWithTopUser = null;
        User currentOpponent = (User) requestValue.getAttribute(CommonConstant.CURRENT_OPPONENT);
        msgStartNumber = 0;

        if (currentOpponent != null) {
            msgStartNumber = numberOfMessagesBetween(currentUserId, currentOpponent.getUserId());
        } else if (!popularUsersList.isEmpty()) {
            msgStartNumber = numberOfMessagesBetween(currentUserId, popularUsersList.get(0).getUserId());
            currentOpponent = popularUsersList.get(0);
            lastMessagesWithTopUser = searchMessagesBetweenWithLimit(currentUserId, currentOpponent.getUserId(), 0, howMuchMessages);

        }

        if (currentOpponent != null) {
            requestValue.servletSessionPut(CommonConstant.LAST_MESSAGE, msgStartNumber);

            if (msgStartNumber - howMuchMessages < 0) {
                lastMessagesWithTopUser = searchMessagesBetweenWithLimit(currentUserId, currentOpponent.getUserId(), 0, howMuchMessages);
            } else {
                lastMessagesWithTopUser = searchMessagesBetweenWithLimit(currentUserId, currentOpponent.getUserId(), msgStartNumber - howMuchMessages, howMuchMessages);
            }
        }


        requestValue.servletSessionPut(CommonConstant.CURRENT_OPPONENT, currentOpponent);
        requestValue.servletSessionPut(CommonConstant.TOP_USERS, popularUsersList);
        requestValue.servletSessionPut(CommonConstant.LAST_MESSAGES, lastMessagesWithTopUser);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void showNextDialogs(RequestValue requestValue) {
        User currentUser = ((User) (requestValue.getAttribute(CommonConstant.CURRENT_USER)));
        long currentUserId = currentUser.getUserId();

        int msgStartNumber = (int) requestValue.getAttribute(CommonConstant.LAST_MESSAGE);

        List<User> users = (List<User>) requestValue.getAttribute(CommonConstant.TOP_USERS);

        List<UserMessage> lastMessagesWithTopUser = null;
        if (!users.isEmpty()) {
            User currentOpponent = (User) requestValue.getAttribute(CommonConstant.CURRENT_OPPONENT);

            int fullSize = numberOfMessagesBetween(currentUserId, currentOpponent.getUserId());
            int howMuchMessages = (int) requestValue.getAttribute(CommonConstant.HOW_MUCH_MESSAGES);

            msgStartNumber = msgStartNumber + howMuchMessages;
            if (msgStartNumber > fullSize) {
                msgStartNumber = fullSize;
            }
            if (msgStartNumber - howMuchMessages < 0) {
                msgStartNumber = howMuchMessages;
            }
            lastMessagesWithTopUser = searchMessagesBetweenWithLimit(currentUserId, currentOpponent.getUserId(), msgStartNumber - howMuchMessages, howMuchMessages);
            requestValue.servletSessionPut(CommonConstant.LAST_MESSAGE, msgStartNumber);

        }

        requestValue.servletSessionPut(CommonConstant.TOP_USERS, users);
        requestValue.servletSessionPut(CommonConstant.LAST_MESSAGES, lastMessagesWithTopUser);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void showPreviousDialogs(RequestValue requestValue) {
        User currentUser = ((User) (requestValue.getAttribute(CommonConstant.CURRENT_USER)));
        long currentUserId = currentUser.getUserId();

        int msgStartNumber = (int) requestValue.getAttribute(CommonConstant.LAST_MESSAGE);

        List<User> users = (List<User>) requestValue.getAttribute(CommonConstant.TOP_USERS);

        List<UserMessage> lastMessagesWithTopUser = null;
        if (!users.isEmpty()) {
            User currentOpponent = (User) requestValue.getAttribute(CommonConstant.CURRENT_OPPONENT);

            int howMuchMessages = (int) requestValue.getAttribute(CommonConstant.HOW_MUCH_MESSAGES);
            msgStartNumber = msgStartNumber - howMuchMessages;
            if (msgStartNumber - howMuchMessages < 0) {
                msgStartNumber = howMuchMessages;
            }

            lastMessagesWithTopUser = searchMessagesBetweenWithLimit(currentUserId, currentOpponent.getUserId(), msgStartNumber - howMuchMessages, howMuchMessages);
            requestValue.servletSessionPut(CommonConstant.LAST_MESSAGE, msgStartNumber);

        }

        requestValue.servletSessionPut(CommonConstant.TOP_USERS, users);
        requestValue.servletSessionPut(CommonConstant.LAST_MESSAGES, lastMessagesWithTopUser);
    }

    @Override
    public void chooseDialogWithUser(RequestValue requestValue) {

        long chosenUserID = Long.parseLong(requestValue.getAttribute(CommonConstant.CHOSEN_USER).toString());

        User chosenUser = null;
        List topUsers = (List) requestValue.getAttribute(CommonConstant.TOP_USERS);

        for (Object o : topUsers) {
            User user = (User) o;
            if (user.getUserId() == chosenUserID) {
                chosenUser = user;
                break;
            }
        }

        requestValue.servletSessionPut(CommonConstant.CURRENT_OPPONENT, chosenUser);

        showDialogs(requestValue);

    }

    @Override
    public void updateTopUsersCash(RequestValue requestValue) {
        User currentUser = (User) requestValue.getAttribute(CommonConstant.CURRENT_USER);
        List<User> popularUsersList = searchPopularUser(currentUser.getUserId(), CommonConstant.HOW_MUCH_USERS_CHAT_PAGE);

        requestValue.servletSessionPut(CommonConstant.TOP_USERS, popularUsersList);
    }


}
