package com.kozitski.pufar.annotation.engine.di.engine.annotation;

import com.kozitski.pufar.annotation.engine.di.engine.entity.DaoImpl;
import com.kozitski.pufar.annotation.engine.di.engine.entity.ServiceImpl;

public class RealApplicationContext implements ApplicationContext{
    private String xmlPath;

    @Override
    public void init(String xmlPath) {

    }

    @Override
    public Object getBean(String name) {
        switch (name){
            case "dao" :
                return new DaoImpl();
            case "service" :
                return new ServiceImpl();
            default:
                return null;
        }
    }

    @Override
    public <T> T getBean(String name, Class<T> calzz) {
        return null;
    }

}
