package com.kozitski.pufar.util.cursor;

import com.kozitski.pufar.entity.notification.UnitType;

import java.util.ArrayList;
import java.util.Arrays;

// TODO: Auto-generated Javadoc
/**
 * The Class NotificationsCursor.
 */
public class NotificationsCursor {
    
    /** The Constant MIN_CURSOR_VALUE. */
    private static final int MIN_CURSOR_VALUE = 0;
    
    /** The max cursor value. */
    private int maxCursorValue = 1_000;

    /** The cursors. */
    private ArrayList<Integer> cursors = new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0));
    
    /** The step. */
    private int step = 6;

    /**
     * Instantiates a new notifications cursor.
     */
    public NotificationsCursor() {
    }
    
    /**
     * Instantiates a new notifications cursor.
     *
     * @param step the step
     */
    public NotificationsCursor(int step) {
        this.step = step;
    }
    
    /**
     * Sets the max cursor value.
     *
     * @param maxCursorValue the new max cursor value
     */
    public void setMaxCursorValue(int maxCursorValue) {
        this.maxCursorValue = maxCursorValue;
    }

    /**
     * Sets the cursor.
     *
     * @param unitType the unit type
     * @param isIncrement the is increment
     * @return the int
     */
    public int setCursor(UnitType unitType, boolean isIncrement){
        int result;

        switch (unitType){
            case OTHER:
                result = makeStep(0, isIncrement);
                break;
            case AUTO:
                result = makeStep(1, isIncrement);
                break;
            case HOME:
                result = makeStep(2, isIncrement);
                break;
            case CHILD:
                result = makeStep(3, isIncrement);
                break;
            case FOOD:
                result = makeStep(4, isIncrement);
                break;
            case WORK:
                result = makeStep(5, isIncrement);
                break;
            case BEAUTY:
                result = makeStep(6, isIncrement);
                break;
            case ELECTRONICS:
                result = makeStep(7, isIncrement);
                break;
            default:
                result = 0;
        }

        return result;
    }
    
    /**
     * Make step.
     *
     * @param position the position
     * @param isIncrement the is increment
     * @return the int
     */
    private int makeStep(int position, boolean isIncrement){
        int result;

        int previous = cursors.get(position);
        if(isIncrement){
            int newValue;
            if(previous + step >= maxCursorValue){
                if(maxCursorValue - step > MIN_CURSOR_VALUE){
                    newValue = maxCursorValue - step;
                }
                else {
                    newValue = 0;
                }
            }
            else {
                newValue = previous + step;
            }

            cursors.set(position, newValue);
            result = newValue;
        }
        else {
            int newValue;

            if(previous - step < MIN_CURSOR_VALUE){
                newValue = MIN_CURSOR_VALUE;
            }
            else{
                newValue = previous - step;
            }

            cursors.set(position, newValue);
            result = newValue;
        }

        return result;
    }

}
