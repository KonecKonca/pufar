package com.kozitski.pufar.command.response;

import com.kozitski.pufar.command.impl.ajax.CheckPasswordCommand;
import com.kozitski.pufar.command.impl.autorization.LogoutCommand;
import com.kozitski.pufar.command.impl.image.LoadImageCommand;
import com.kozitski.pufar.command.impl.image.ShowImageCommand;

public enum CommandTypeWithResponse {

    SHOW_IMAGE(new ShowImageCommand()),
    LOAD_IMAGE(new LoadImageCommand()),
    CHECK_PASSWORDS(new CheckPasswordCommand()),
    LOGOUT(new LogoutCommand());

    private ResponseCommand command;

    CommandTypeWithResponse(ResponseCommand command) {
        this.command = command;
    }

    public ResponseCommand getCommand() {
        return command;
    }

}
