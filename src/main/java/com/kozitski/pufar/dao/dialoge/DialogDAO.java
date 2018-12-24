package com.kozitski.pufar.dao.dialoge;

import com.kozitski.pufar.dao.PufarDao;
import com.kozitski.pufar.entity.message.UserMessage;
import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.exception.PufarDAOException;

import java.util.ArrayList;
import java.util.List;

public interface DialogDAO{

    List<UserMessage> searchAllMessagesFromTo(long fromUserId, long toUserId);
    List<UserMessage> searchAllMessagesBetween(long userId1, long userId2);
    List<UserMessage> searchMessagesBetweenWithLimit(long userId1, long userId2, int since, int howMuch);

    ArrayList<User> searchPopularUser(long forWhomUserId, int howMuch);

    void addMessage(long senderId, long receiverId, String message) throws PufarDAOException;

}
