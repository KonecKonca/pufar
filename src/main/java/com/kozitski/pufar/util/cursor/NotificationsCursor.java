package com.kozitski.pufar.util.cursor;

import com.kozitski.pufar.entity.notification.UnitType;

import java.util.ArrayList;
import java.util.Arrays;

public class NotificationsCursor {
    private static final int MIN_CURSOR_VALUE = 0;
    private int maxCursorValue = 1_000;

    private ArrayList<Integer> cursors = new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0));
    private int step = 6;

    public NotificationsCursor() {
    }
    public NotificationsCursor(int step) {
        this.step = step;
    }
    public void setMaxCursorValue(int maxCursorValue) {
        this.maxCursorValue = maxCursorValue;
    }

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
