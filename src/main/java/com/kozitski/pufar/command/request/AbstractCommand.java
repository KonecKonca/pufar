package com.kozitski.pufar.command.request;

import com.kozitski.pufar.command.InjectService;
import com.kozitski.pufar.command.RequestValue;
import com.kozitski.pufar.command.Router;
import com.kozitski.pufar.util.path.WebPathReturner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Properties;

public abstract class AbstractCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCommand.class);
    private static final String COMMAND_INJECTION_PROPERTY_PATH = "\\WEB-INF\\classes\\injection\\commandInjection.properties";

    public AbstractCommand() {
        init();
    }
    private void init(){

        Properties properties = new Properties();
        try(FileInputStream fileInputStream = new FileInputStream(WebPathReturner.webPath + COMMAND_INJECTION_PROPERTY_PATH)){
            properties.load(fileInputStream);
        }
        catch (IOException e){
            LOGGER.error("Services are not injected, case property not downloaded", e);
        }

        for(Field field : getClass().getDeclaredFields()){
            field.setAccessible(true);

            InjectService annotation = field.getAnnotation(InjectService.class);
            if(annotation != null){

                String injectedType = field.getType().getSimpleName();
                String injectedRealType = properties.get(injectedType).toString();

                try {
                    field.set(this, Class.forName(injectedRealType).newInstance());
                }
                catch (IllegalAccessException | ClassNotFoundException | InstantiationException e) {
                    LOGGER.error("Field with type " + field.getType() + ", with name " + field.getName() + " wasn't initialize", e);
                }
            }

        }

    }

    public abstract Router execute(RequestValue request);

}
