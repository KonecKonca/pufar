package com.kozitski.pufar.command.impl.dialog;

import com.kozitski.pufar.command.AbstractCommand;
import com.kozitski.pufar.command.PagePath;
import com.kozitski.pufar.command.RequestValue;
import com.kozitski.pufar.command.Router;
import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.service.dialoge.DialogService;
import com.kozitski.pufar.service.dialoge.DialogServiceImpl;
import com.kozitski.pufar.util.CommonConstant;

public class SendMessageCommand extends AbstractCommand {
    private static final String SENDING_MESSAGE = "sentValue";

    private DialogService dialogService = new DialogServiceImpl();

    @Override
    public Router execute(RequestValue requestValue) {

        String sentMessage = requestValue.getAttribute(SENDING_MESSAGE).toString();
        User currentUser = (User) requestValue.getAttribute(CommonConstant.CURRENT_USER);
        User currentOpponent = (User) requestValue.getAttribute(CommonConstant.CURRENT_OPPONENT);

        dialogService.addMessage(currentUser.getUserId(), currentOpponent.getUserId(), sentMessage);

        // todo: перенести теперешний функционал комманд в service
        return new GetContactCommand().execute(requestValue);
    }


}
