package com.kozitski.pufar.annotation.engine.validation.entity;

import com.kozitski.pufar.annotation.engine.validation.annotation.Validate;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface Validatable<T> {

    default boolean validate(T t){
        boolean result = true;

        Field[] fields = t.getClass().getDeclaredFields();
        for (Field field : fields){
            field.setAccessible(true);
            Validate annotation = field.getAnnotation(Validate.class);

            if(annotation != null && field.getType() != annotation.type()){
                result = false;
            }
            else if(annotation != null){
                try {
                    result = validateLogic(annotation, (String) field.get(t));
                }
                catch (IllegalAccessException e) {

                    // must be handled

                    e.printStackTrace();
                }
            }

        }


        return result;
    }

    static boolean validateLogic(Validate annotation, String checkedValue){
        boolean result = true;

        int minLength = annotation.minLength();
        int maxLength = annotation.maxLength();
        String forbiddenValue = annotation.forbiddenValue();

        String validateRegExp = annotation.validateRegExp();

        if(checkedValue.length() < minLength ||checkedValue.length() > maxLength || checkedValue.equalsIgnoreCase(forbiddenValue)){
            result = false;
        }

        if(!checkedValue.matches(validateRegExp)){
            result = false;
        }

        return result;
    }

}
