package com.kozitski.pufar.command;

import com.kozitski.pufar.command.impl.*;
import com.kozitski.pufar.command.impl.admin.AdminControlPanelCommand;
import com.kozitski.pufar.command.impl.admin.notification.choose.ChangeNotificationMessageCommand;
import com.kozitski.pufar.command.impl.admin.notification.choose.SearchNotificationCommand;
import com.kozitski.pufar.command.impl.admin.notification.choose.DropCommentCommand;
import com.kozitski.pufar.command.impl.admin.notification.choose.DropNotificationCommand;
import com.kozitski.pufar.command.impl.admin.notification.execute.SearchNotificationExecuteCommand;
import com.kozitski.pufar.command.impl.admin.user.choose.*;
import com.kozitski.pufar.command.impl.admin.user.execute.SearchUserCommand;
import com.kozitski.pufar.command.impl.autorization.LoginCommand;
import com.kozitski.pufar.command.impl.autorization.RegistrationCommand;
import com.kozitski.pufar.command.impl.dialog.*;
import com.kozitski.pufar.command.impl.error.ErrorCommand;
import com.kozitski.pufar.command.impl.notification.*;
import com.kozitski.pufar.command.impl.notification.additional.PutMarkCommand;
import com.kozitski.pufar.command.impl.notification.additional.SentCommentCommand;
import com.kozitski.pufar.command.impl.notification.additional.SentMessageToAuthorCommand;
import com.kozitski.pufar.command.impl.notification.additional.ShowAdditionalNotificationCommand;
import com.kozitski.pufar.command.impl.profile.ChangeNumberCommand;
import com.kozitski.pufar.command.impl.profile.ChangePasswordCommand;
import com.kozitski.pufar.command.impl.profile.DropMyselfNotificationCommand;
import com.kozitski.pufar.command.impl.profile.OpenProfilePageCommand;

public enum CommandType {

    INDEX(new IndexCommand()),
    GET_CONTACT(new GetContactCommand()),
    LOGIN(new LoginCommand()),
    ERROR(new ErrorCommand()),
    REGISTRATION(new RegistrationCommand()),
    MESSAGE_NEXT(new NextMessageCommand()),
    MESSAGE_PREVIOUS(new PreviousMessageCommand()),
    SEND_MESSAGE(new SendMessageCommand()),
    CHANGE_OPPONENT(new ChangeOpponent()),
    CHOOSE_CREATE_NOTIFICATION(new ChooseCreateNotificationCommand()),
    CREATE_NOTIFICATION(new CreateNotificationCommand()),
    SHOW_NOTIFICATION_BY_UNIT_TYPE(new ShowNotificationByUnitTypeCommand()),
    SHOW_ADDITIONAL_NOTIFICATION(new ShowAdditionalNotificationCommand()),
    CHANGE_NOTIFICATION(new ChangeNotificationCommand()),
    SENT_COMMENT(new SentCommentCommand()),
    PUT_MARK(new PutMarkCommand()),
    SENT_MESSAGE_TO_NOTIFICATION_AUTHOR(new SentMessageToAuthorCommand()),

    // profile page
    OPEN_PROFILE_PAGE(new OpenProfilePageCommand()),
    DROP_MYSELF_NOTIFICATION(new DropMyselfNotificationCommand()),
    CHANGE_MOBILE_PHONE(new ChangeNumberCommand()),
    CHANGE_PASSWORD(new ChangePasswordCommand()),

    // admin commands
        // define inputPage
    BAN_USER(new BanUserCommand()),
    UNBAN_USER(new UnBanUserCommand()),
    CHANGE_USER_LOGIN(new ChangeUserLoginCommand()),
    CHANGE_USER_STATUS(new ChangeUserStatusCommand()),
    DROP_NOTIFICATION(new DropNotificationCommand()),
    CHANGE_NOTIFICATION_MESSAGE(new ChangeNotificationMessageCommand()),
    DROP_COMMENT(new DropCommentCommand()),
    CHOOSE_USER(new ChooseUserCommand()),
    CHOOSE_NOTIFICATION(new SearchNotificationCommand()),
    CONTROL_PANEL(new AdminControlPanelCommand()),
        // execute search
    CHOSE_USER_EXECUTE(new SearchUserCommand()),
    CHOSE_NOTIFICATION_EXECUTE(new SearchNotificationExecuteCommand());

    private AbstractCommand command;

    CommandType(AbstractCommand command) {
        this.command = command;
    }
    public AbstractCommand getCommand() {
        return command;
    }

}
