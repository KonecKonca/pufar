package com.kozitski.pufar.service.dialoge;

import com.kozitski.pufar.dao.dialoge.DialogDAO;
import com.kozitski.pufar.dao.dialoge.MySQLDialogDao;
import com.kozitski.pufar.entity.message.UserMessage;
import com.kozitski.pufar.entity.user.User;

import java.util.ArrayList;
import java.util.List;

public class DialogServiceImpl implements DialogService {

    private DialogDAO dialogDAO = new MySQLDialogDao();

    @Override
    public List<UserMessage> searchAllMessagesFromTo(long fromUserId, long toUserId) {

        // validation
        return dialogDAO.searchAllMessagesFromTo(fromUserId, toUserId);
    }
    @Override
    public List<UserMessage> searchAllMessagesBetween(long userId1, long userId2) {

        // validation
        return dialogDAO.searchAllMessagesBetween(userId1, userId2);
    }
    @Override
    public List<UserMessage> searchMessagesBetweenWithLimit(long userId1, long userId2, int since, int howMuch) {

        // validation
        return dialogDAO.searchMessagesBetweenWithLimit(userId1, userId2, since, howMuch);
    }
    @Override
    public ArrayList<User> searchPopularUser(long forWhomUserId, int howMuch) {
        return dialogDAO.searchPopularUser(forWhomUserId, howMuch);
    }


}
