package com.kozitski.pufar.annotation.engine.di.engine.entity;

public class ServiceImpl {
    private String serviceName = "Any Service data";

    public String getServiceName() {
        return serviceName;
    }
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public String toString() {
        return "ServiceImpl{" +
                "serviceName='" + serviceName + '\'' +
                '}';
    }

}
