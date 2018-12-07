package com.kozitski.pufar.annotation.engine.di.engine.entity;


public class DaoImpl {
    private String name = "any DAO data";

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "DaoImpl{" +
                "name='" + name + '\'' +
                '}';
    }

}
