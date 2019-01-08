package com.kozitski.pufar.command.request;

import com.kozitski.pufar.command.impl.IndexCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommandFactory.class);

    private CommandFactory() { }

    public static AbstractCommand chooseCommand(String commandName){
        AbstractCommand command = new IndexCommand();

        if(commandName == null || commandName.isEmpty()){
            return command;
        }

        try {
            CommandType type = CommandType.valueOf(commandName.toUpperCase());
            command = type.getCommand();
            LOGGER.info("Command defined correctly: [" + commandName.toUpperCase() + "]");
        }
        catch (IllegalArgumentException e){
            LOGGER.warn("Command is not found");
        }


        return command;
    }

}
