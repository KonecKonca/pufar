package com.kozitski.pufar.annotation.engine.di.engine.annotation;


import com.kozitski.pufar.annotation.engine.di.engine.entity.DaoImpl;
import com.kozitski.pufar.annotation.engine.di.engine.entity.ServiceImpl;

public class MockApplicationContext implements ApplicationContext {
    private String xmlPath;

    @Override
    public void init(String xmlPath) {
        this.xmlPath = xmlPath;
    }

    @Override
    public Object getBean(String name) {
        return getBean(name, Object.class);
    }

    @Override
    public <T> T getBean(String name, Class<T> clazz) {

        if(name.equalsIgnoreCase("service")){
            return (T) new ServiceImpl();
        }
        else if(name.equalsIgnoreCase("dao")){
            return (T)new DaoImpl();
        }
        else {
            throw new IllegalArgumentException();
        }

    }



}
