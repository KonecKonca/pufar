package com.kozitski.pufar.annotation.engine.di.engine.entity;


import com.kozitski.pufar.annotation.engine.di.engine.annotation.Inject;

public class Controller {

    @Inject(beanName = "dao")
    private DaoImpl daoImpl;

    @Inject(beanName = "service")
    private ServiceImpl serviceImpl;

    public void setDaoImpl(DaoImpl daoImpl) {
        this.daoImpl = daoImpl;
    }
    public void setServiceImpl(ServiceImpl serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public String toString() {
        return  "serviceImpl=" + serviceImpl +
                ", daoImpl=" + daoImpl;
    }

}
