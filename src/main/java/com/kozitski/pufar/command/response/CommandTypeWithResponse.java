package com.kozitski.pufar.command.response;

import com.kozitski.pufar.command.impl.autorization.LogoutCommand;

public enum CommandTypeWithResponse {

    LOGOUT(new LogoutCommand());

    private ResourceCommand command;

    CommandTypeWithResponse(ResourceCommand command) {
        this.command = command;
    }

    public ResourceCommand getCommand() {
        return command;
    }

}
