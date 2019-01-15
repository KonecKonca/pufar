package com.kozitski.pufar.service.dialoge;

import com.kozitski.pufar.command.RequestValue;
import com.kozitski.pufar.entity.message.UserMessage;
import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.exception.PufarValidationException;
import com.kozitski.pufar.validation.annotation.AspectValid;
import com.kozitski.pufar.validation.annotation.primitive.string.StringValid;

import java.util.List;

/**
 * The Interface DialogService.
 */
public interface DialogService {

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
     * Number of messages between.
     *
     * @param userId1 the user id 1
     * @param userId2 the user id 2
     * @return the int
     */
    int numberOfMessagesBetween(long userId1, long userId2);

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
     * @throws PufarValidationException the pufar validation exception
     */
    @AspectValid
    void addMessage(long senderId, long receiverId, @StringValid String message) throws PufarValidationException;

    /**
     * Show dialogs.
     *
     * @param requestValue the request value
     */
    void showDialogs(RequestValue requestValue);

    /**
     * Show next dialogs.
     *
     * @param requestValue the request value
     */
    void showNextDialogs(RequestValue requestValue);

    /**
     * Show previous dialogs.
     *
     * @param requestValue the request value
     */
    void showPreviousDialogs(RequestValue requestValue);

    /**
     * Choose dialog with user.
     *
     * @param requestValue the request value
     */
    void chooseDialogWithUser(RequestValue requestValue);

    /**
     * Update top users cash.
     *
     * @param requestValue the request value
     */
    void updateTopUsersCash(RequestValue requestValue);

}
