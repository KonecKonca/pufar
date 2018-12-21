package com.kozitski.pufar.command.impl.notification;

import com.kozitski.pufar.command.AbstractCommand;
import com.kozitski.pufar.command.RequestValue;
import com.kozitski.pufar.command.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateNotificationCommand extends AbstractCommand {
    private static Logger LOGGER = LoggerFactory.getLogger(CreateNotificationCommand.class);

    @Override
    public Router execute(RequestValue request) {
        System.out.println("!!!!!!!!!!!!!!!!!!!!");
        return null;
    }

}
