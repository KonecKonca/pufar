package com.kozitski.pufar.command.request;

import com.kozitski.pufar.command.impl.IndexCommand;
import com.kozitski.pufar.command.impl.admin.AdminControlPanelCommand;
import com.kozitski.pufar.command.impl.admin.notification.ChangeNotificationMessageCommand;
import com.kozitski.pufar.command.impl.admin.notification.DropCommentCommand;
import com.kozitski.pufar.command.impl.admin.notification.DropNotificationCommand;
import com.kozitski.pufar.command.impl.admin.notification.SearchNotificationCommand;
import com.kozitski.pufar.command.impl.admin.notification.SearchNotificationExecuteCommand;
import com.kozitski.pufar.command.impl.admin.user.*;
import com.kozitski.pufar.command.impl.autorization.LoginCommand;
import com.kozitski.pufar.command.impl.autorization.RegistrationCommand;
import com.kozitski.pufar.command.impl.dialog.*;
import com.kozitski.pufar.command.impl.locale.ChangeLocalCommand;
import com.kozitski.pufar.command.impl.notification.ChangeNotificationCommand;
import com.kozitski.pufar.command.impl.notification.ChooseCreateNotificationCommand;
import com.kozitski.pufar.command.impl.notification.CreateNotificationCommand;
import com.kozitski.pufar.command.impl.notification.ShowNotificationByUnitTypeCommand;
import com.kozitski.pufar.command.impl.notification.additional.PutMarkCommand;
import com.kozitski.pufar.command.impl.notification.additional.SentCommentCommand;
import com.kozitski.pufar.command.impl.notification.additional.SentMessageToAuthorCommand;
import com.kozitski.pufar.command.impl.notification.additional.ShowAdditionalNotificationCommand;
import com.kozitski.pufar.command.impl.profile.ChangeNumberCommand;
import com.kozitski.pufar.command.impl.profile.ChangePasswordCommand;
import com.kozitski.pufar.command.impl.profile.DropMyselfNotificationCommand;
import com.kozitski.pufar.command.impl.profile.OpenProfilePageCommand;

/**
 * The enum Command types which receive RequestValue
 * wrapper, not request and response
 */
public enum CommandType {

    /**
     * The Index.
     */
    INDEX(new IndexCommand()),
    /**
     * The Get contact.
     */
    GET_CONTACT(new GetContactCommand()),
    /**
     * The Login.
     */
    LOGIN(new LoginCommand()),
    /**
     * The Registration.
     */
    REGISTRATION(new RegistrationCommand()),
    /**
     * The Message next.
     */
    MESSAGE_NEXT(new NextMessageCommand()),
    /**
     * The Message previous.
     */
    MESSAGE_PREVIOUS(new PreviousMessageCommand()),
    /**
     * The Send message.
     */
    SEND_MESSAGE(new SendMessageCommand()),
    /**
     * The Change opponent.
     */
    CHANGE_OPPONENT(new ChangeOpponent()),
    /**
     * The Choose create notification.
     */
    CHOOSE_CREATE_NOTIFICATION(new ChooseCreateNotificationCommand()),
    /**
     * The Create notification.
     */
    CREATE_NOTIFICATION(new CreateNotificationCommand()),
    /**
     * The Show notification by unit type.
     */
    SHOW_NOTIFICATION_BY_UNIT_TYPE(new ShowNotificationByUnitTypeCommand()),
    /**
     * The Show additional notification.
     */
    SHOW_ADDITIONAL_NOTIFICATION(new ShowAdditionalNotificationCommand()),
    /**
     * The Change notification.
     */
    CHANGE_NOTIFICATION(new ChangeNotificationCommand()),
    /**
     * The Sent comment.
     */
    SENT_COMMENT(new SentCommentCommand()),
    /**
     * The Put mark.
     */
    PUT_MARK(new PutMarkCommand()),
    /**
     * The Sent message to notification author.
     */
    SENT_MESSAGE_TO_NOTIFICATION_AUTHOR(new SentMessageToAuthorCommand()),

    /**
     * The Open profile page.
     */
// profile page
    OPEN_PROFILE_PAGE(new OpenProfilePageCommand()),
    /**
     * The Drop myself notification.
     */
    DROP_MYSELF_NOTIFICATION(new DropMyselfNotificationCommand()),
    /**
     * The Change mobile phone.
     */
    CHANGE_MOBILE_PHONE(new ChangeNumberCommand()),
    /**
     * The Change password.
     */
    CHANGE_PASSWORD(new ChangePasswordCommand()),

    /**
     * The Change locale.
     */
// locale
    CHANGE_LOCALE(new ChangeLocalCommand()),

    /**
     * The Ban user.
     */
// admin commands
    // define inputPage
    BAN_USER(new BanUserCommand()),
    /**
     * The Unban user.
     */
    UNBAN_USER(new UnBanUserCommand()),
    /**
     * The Change user login.
     */
    CHANGE_USER_LOGIN(new ChangeUserLoginCommand()),
    /**
     * The Change user status.
     */
    CHANGE_USER_STATUS(new ChangeUserStatusCommand()),
    /**
     * The Drop notification.
     */
    DROP_NOTIFICATION(new DropNotificationCommand()),
    /**
     * The Change notification message.
     */
    CHANGE_NOTIFICATION_MESSAGE(new ChangeNotificationMessageCommand()),
    /**
     * The Drop comment.
     */
    DROP_COMMENT(new DropCommentCommand()),
    /**
     * The Choose user.
     */
    CHOOSE_USER(new ChooseUserCommand()),
    /**
     * The Choose notification.
     */
    CHOOSE_NOTIFICATION(new SearchNotificationCommand()),
    /**
     * The Control panel.
     */
    CONTROL_PANEL(new AdminControlPanelCommand()),
    /**
     * The Chose user execute.
     */
// execute search
    CHOSE_USER_EXECUTE(new SearchUserCommand()),
    /**
     * The Chose notification execute.
     */
    CHOSE_NOTIFICATION_EXECUTE(new SearchNotificationExecuteCommand());

    private AbstractCommand command;

    CommandType(AbstractCommand command) {
        this.command = command;
    }

    /**
     * Gets command.
     *
     * @return the command
     */
    public AbstractCommand getCommand() {
        return command;
    }

}
