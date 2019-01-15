package com.kozitski.pufar.entity.notification;

public enum UnitType {

    OTHER,
    AUTO,
    HOME,
    CHILD,
    FOOD,
    WORK,
    BEAUTY,
    ELECTRONICS;

    public static int getUnitDBPosition(UnitType unitType){
        int result;

        switch (unitType){
            case OTHER:
                result = 1;
                break;
            case AUTO:
                result = 2;
                break;
            case HOME:
                result = 3;
                break;
            case CHILD:
                result = 4;
                break;
            case FOOD:
                result = 5;
                break;
            case WORK:
                result = 6;
                break;
            case BEAUTY:
                result = 7;
                break;
            case ELECTRONICS:
                result = 8;
                break;
            default:
                result = 1;
        }

        return result;
    }

}
