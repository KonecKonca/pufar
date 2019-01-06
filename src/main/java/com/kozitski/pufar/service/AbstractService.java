package com.kozitski.pufar.service;

import com.kozitski.pufar.util.path.WebPathReturner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Properties;

public abstract class AbstractService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractService.class);
    private static final String SERVICE_INJECTION_PROPERTY_PATH = "\\WEB-INF\\classes\\injection\\serviceInjection.properties";

    public AbstractService() {
        init();
    }

    private void init() {

        Properties properties = new Properties();
        try(FileInputStream fileInputStream = new FileInputStream(WebPathReturner.webPath + SERVICE_INJECTION_PROPERTY_PATH)){
            properties.load(fileInputStream);
        }
        catch (IOException e){
            LOGGER.error("Dao are not injected, case property not downloaded", e);
        }

        for(Field field : getClass().getDeclaredFields()){
            field.setAccessible(true);

            InjectDao annotation = field.getAnnotation(InjectDao.class);
            if(annotation != null){

                String injectedRealType = properties.get(field.getType().getSimpleName()).toString();

                try {
                    field.set(this, Class.forName(injectedRealType).newInstance());
                }
                catch (IllegalAccessException | ClassNotFoundException | InstantiationException e) {
                    LOGGER.error("Field with type " + field.getType() + ", with name " + field.getName() + " wasn't initialize", e);
                }
            }

        }

    }

}
