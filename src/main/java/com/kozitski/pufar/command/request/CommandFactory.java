package com.kozitski.pufar.command.request;

import com.kozitski.pufar.command.impl.IndexCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A factory for creating Command objects, which
 * receive RequestValue
 */
public class CommandFactory {
    
    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(CommandFactory.class);

    /**
     * Instantiates a new command factory.
     */
    private CommandFactory() { }

    /**
     * Choose command.
     *
     * @param commandName the command name
     * @return the abstract command
     */
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
