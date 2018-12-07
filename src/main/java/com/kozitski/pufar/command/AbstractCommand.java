package com.kozitski.pufar.command;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;

public abstract class AbstractCommand {

    private void init(){

        // wonders of dependency injection

        for(Field field : this.getClass().getDeclaredFields()){
            field.setAccessible(true);

            InjectService annotation = field.getAnnotation(InjectService.class);

            if(annotation != null){
                try {
                    field.set(this, Class.forName("Any path from property"));
                } catch (IllegalAccessException | ClassNotFoundException e) {
                    // error log
                    e.printStackTrace();
                }
            }
            else {

                // logger error

            }

        }

    }

    public AbstractCommand() {
        init();
    }

    public abstract Router execute(RequestValue request);

}
