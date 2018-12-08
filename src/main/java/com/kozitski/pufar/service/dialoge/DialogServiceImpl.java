package com.kozitski.pufar.service.dialoge;

import com.kozitski.pufar.dao.dialoge.DialogDAO;
import com.kozitski.pufar.dao.dialoge.MySQLDialogDao;
import com.kozitski.pufar.entity.message.UserMessage;
import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.validation.annotation.primitive.string.StringValid;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class DialogServiceImpl implements DialogService {
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

        String utf8Message = new String(message.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        dialogDAO.addMessage(senderId, receiverId, utf8Message);

    }

}
