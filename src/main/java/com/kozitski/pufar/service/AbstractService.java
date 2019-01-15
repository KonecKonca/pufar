package com.kozitski.pufar.service;

import com.kozitski.pufar.util.path.WebPathReturner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Properties;

/**
 * The Class AbstractService.
 * Super class for all services.
 */
public abstract class AbstractService {
    
    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractService.class);
    
    /** The Constant SERVICE_INJECTION_PROPERTY_PATH. */
    private static final String SERVICE_INJECTION_PROPERTY_PATH = "/WEB-INF/classes/injection/serviceInjection.properties";

    /**
     * Instantiates a new abstract service.
     */
    public AbstractService() {
        init();
    }

    /**
     * Inits the makes dependency injection
     * for all necessary fields
     */
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

                String injectedType = field.getType().getSimpleName();
                String injectedRealType = null;

                Object propertyElem = properties.get(injectedType);

                if(propertyElem != null){
                    injectedRealType = propertyElem.toString();

                    if(injectedRealType!= null && !injectedRealType.isEmpty()){

                        try {
                            Object injectedDao = null;
                            Class<?> clazz = Class.forName(injectedRealType);

                            if(clazz != null){
                                injectedDao = clazz.newInstance();
                                field.set(this, injectedDao);
                            }

                        }
                        catch (IllegalAccessException | ClassNotFoundException | InstantiationException e) {
                            LOGGER.error("Field with type " + field.getType() + ", with name " + field.getName() + " wasn't initialize", e);
                        }

                    }

                }

            }

        }

    }

}
