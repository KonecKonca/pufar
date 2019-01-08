package com.kozitski.pufar.command.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandFactoryWithResponse {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommandFactoryWithResponse.class);

    private CommandFactoryWithResponse() { }

    public static ResourceCommand chooseCommand(String commandName){
        ResourceCommand command = null;

        if(commandName == null || commandName.isEmpty()){
            return command;
        }

        try {
            CommandTypeWithResponse type = CommandTypeWithResponse.valueOf(commandName.toUpperCase());
            command = type.getCommand();
            LOGGER.info("Command defined correctly: [" + commandName.toUpperCase() + "]");
        }
        catch (IllegalArgumentException e){
            LOGGER.warn("Command is not found");
        }


        return command;
    }

}
