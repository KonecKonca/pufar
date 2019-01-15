package com.kozitski.pufar.entity.notification;

/**
 * The enum Unit type.
 */
public enum UnitType {

    /**
     * Other unit type.
     */
    OTHER,
    /**
     * Auto unit type.
     */
    AUTO,
    /**
     * Home unit type.
     */
    HOME,
    /**
     * Child unit type.
     */
    CHILD,
    /**
     * Food unit type.
     */
    FOOD,
    /**
     * Work unit type.
     */
    WORK,
    /**
     * Beauty unit type.
     */
    BEAUTY,
    /**
     * Electronics unit type.
     */
    ELECTRONICS;

    /**
     * Get unit db position int.
     *
     * @param unitType the unit type
     * @return the int
     */
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
