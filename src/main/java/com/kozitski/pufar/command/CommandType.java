package com.kozitski.pufar.command;

import com.kozitski.pufar.command.impl.*;
import com.kozitski.pufar.command.impl.admin.AdminControlPanelCommand;
import com.kozitski.pufar.command.impl.admin.notification.ChangeNotificationMessageCommand;
import com.kozitski.pufar.command.impl.admin.notification.ChooseNotificationCommand;
import com.kozitski.pufar.command.impl.admin.notification.DropCommentCommand;
import com.kozitski.pufar.command.impl.admin.notification.DropNotificationCommand;
import com.kozitski.pufar.command.impl.admin.user.ChangeUserLoginCommand;
import com.kozitski.pufar.command.impl.admin.user.ChangeUserStatusCommand;
import com.kozitski.pufar.command.impl.admin.user.ChooseUserCommand;
import com.kozitski.pufar.command.impl.admin.user.DropUserCommand;
import com.kozitski.pufar.command.impl.autorization.LoginCommand;
import com.kozitski.pufar.command.impl.autorization.RegistrationCommand;
import com.kozitski.pufar.command.impl.dialog.*;
import com.kozitski.pufar.command.impl.error.ErrorCommand;
import com.kozitski.pufar.command.impl.locale.SetEnLocaleCommand;
import com.kozitski.pufar.command.impl.locale.SetRuLocaleCommand;

public enum CommandType {

    INDEX(new IndexCommand()),
    GET_CONTACT(new GetContactCommand()),
    LOGIN(new LoginCommand()),
    ERROR(new ErrorCommand()),
    CHANGE_LOCALE_RU(new SetRuLocaleCommand()),
    CHANGE_LOCALE_EN(new SetEnLocaleCommand()),
    REGISTRATION(new RegistrationCommand()),
    MESSAGE_NEXT(new NextMessageCommand()),
    MESSAGE_PREVIOUS(new PreviousMessageCommand()),
    SEND_MESSAGE(new SendMessageCommand()),
    CHANGE_OPPONENT(new ChangeOpponent()),


    LOGOUT(new IndexCommand()),
    SHOW_DIALOGS(new IndexCommand()),
    SHOW_DIALOG(new IndexCommand()),
    SHOW_NOTIFICATIONS(new IndexCommand()),
    SHOW_NOTIFICATION(new IndexCommand()),
    PROFILE(new IndexCommand()),
    SHOW_INFO_PAGE(new IndexCommand()),

    // admin commands
    DROP_USER(new DropUserCommand()),
    CHANGE_USER_LOGIN(new ChangeUserLoginCommand()),
    CHANGE_USER_STATUS(new ChangeUserStatusCommand()),
    DROP_NOTIFICATION(new DropNotificationCommand()),
    CHANGE_NOTIFICATION_MESSAGE(new ChangeNotificationMessageCommand()),
    DROP_COMMENT(new DropCommentCommand()),
    CHOOSE_USER(new ChooseUserCommand()),
    CHOOSE_NOTIFICATION(new ChooseNotificationCommand()),
    CONTROL_PANEL(new AdminControlPanelCommand());



    private AbstractCommand command;

    CommandType(AbstractCommand command) {
        this.command = command;
    }
    public AbstractCommand getCommand() {
        return command;
    }

}
