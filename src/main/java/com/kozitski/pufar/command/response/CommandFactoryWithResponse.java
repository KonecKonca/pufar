package com.kozitski.pufar.command.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class CommandFactoryWithResponse.
 */
public class CommandFactoryWithResponse {
    
    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(CommandFactoryWithResponse.class);

    /**
     * Instantiates a new command factory with response.
     */
    private CommandFactoryWithResponse() { }

    /**
     * Choose command.
     *
     * @param commandName the command name
     * @return the response command
     */
    public static ResponseCommand chooseCommand(String commandName){
        ResponseCommand command = null;

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
