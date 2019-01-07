package com.kozitski.pufar.service.dialoge;

import com.kozitski.pufar.command.RequestValue;
import com.kozitski.pufar.entity.message.UserMessage;
import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.exception.PufarValidationException;
import com.kozitski.pufar.validation.annotation.AspectValid;
import com.kozitski.pufar.validation.annotation.primitive.string.StringValid;

import java.util.List;

public interface DialogService {

    List<UserMessage> searchAllMessagesFromTo(long fromUserId, long toUserId);

    List<UserMessage> searchAllMessagesBetween(long userId1, long userId2);

    List<UserMessage> searchMessagesBetweenWithLimit(long userId1, long userId2, int since, int howMuch);

    int numberOfMessagesBetween(long userId1, long userId2);

    List<User> searchPopularUser(long forWhomUserId, int howMuch);

    @AspectValid
    void addMessage(long senderId, long receiverId, @StringValid String message) throws PufarValidationException;

    void showDialogs(RequestValue requestValue);

    void showNextDialogs(RequestValue requestValue);

    void showPreviousDialogs(RequestValue requestValue);

    void chooseDialogWithUser(RequestValue requestValue);

    void updateTopUsersCash(RequestValue requestValue);

}
