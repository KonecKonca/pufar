package com.kozitski.pufar.dao.dialoge;

import com.kozitski.pufar.entity.message.UserMessage;
import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.exception.PufarDAOException;

import java.util.List;

/**
 * The Interface DialogDao.
 */
public interface DialogDao {

    /**
     * Search all messages from to.
     *
     * @param fromUserId the from user id
     * @param toUserId the to user id
     * @return the list
     */
    List<UserMessage> searchAllMessagesFromTo(long fromUserId, long toUserId);

    /**
     * Search all messages between.
     *
     * @param userId1 the user id 1
     * @param userId2 the user id 2
     * @return the list
     */
    List<UserMessage> searchAllMessagesBetween(long userId1, long userId2);

    /**
     * Search messages between with limit.
     *
     * @param userId1 the user id 1
     * @param userId2 the user id 2
     * @param since the since
     * @param howMuch the how much
     * @return the list
     */
    List<UserMessage> searchMessagesBetweenWithLimit(long userId1, long userId2, int since, int howMuch);

    /**
     * Search popular user.
     *
     * @param forWhomUserId the for whom user id
     * @param howMuch the how much
     * @return the list
     */
    List<User> searchPopularUser(long forWhomUserId, int howMuch);

    /**
     * Adds the message.
     *
     * @param senderId the sender id
     * @param receiverId the receiver id
     * @param message the message
     * @throws PufarDAOException the pufar DAO exception
     */
    void addMessage(long senderId, long receiverId, String message) throws PufarDAOException;

}
