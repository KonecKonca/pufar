package com.kozitski.pufar.command.response;

import com.kozitski.pufar.command.impl.ajax.CheckPasswordCommand;
import com.kozitski.pufar.command.impl.autorization.LogoutCommand;
import com.kozitski.pufar.command.impl.image.LoadImageCommand;
import com.kozitski.pufar.command.impl.image.ShowImageCommand;

/**
 * The enum Command types which receive request and response
 */
public enum CommandTypeWithResponse {

    /**
     * The Show image.
     */
    SHOW_IMAGE(new ShowImageCommand()),
    /**
     * The Load image.
     */
    LOAD_IMAGE(new LoadImageCommand()),
    /**
     * The Check passwords.
     */
    CHECK_PASSWORDS(new CheckPasswordCommand()),
    /**
     * The Logout.
     */
    LOGOUT(new LogoutCommand());

    private ResponseCommand command;

    CommandTypeWithResponse(ResponseCommand command) {
        this.command = command;
    }

    /**
     * Gets command.
     *
     * @return the command
     */
    public ResponseCommand getCommand() {
        return command;
    }

}
