package com.kozitski.pufar.validation.validator.primitive;

import com.kozitski.pufar.validation.annotation.primitive.string.StringValid;
import com.kozitski.pufar.validation.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.annotation.Annotation;

public class StringValidator implements Validator {
    private static Logger LOGGER = LoggerFactory.getLogger(StringValidator.class);
    
    @Override
    public void validate(Annotation[] annotations, Object object) throws RuntimeException {
        for(Annotation annotation : annotations){
            if(annotation instanceof StringValid && object instanceof String){
                stringValidation((StringValid) annotation, (String) object);
            }
        }
    }

    private void stringValidation(StringValid annotation, String string) throws RuntimeException {

        if(string == null){
            LOGGER.error("String can not be null");
            throw new RuntimeException("String can not be null");
        }

        int minLength = annotation.minLength();
        int maxLength = annotation.maxLength();
        String forbiddenValue = annotation.forbiddenValue();

        String validateRegExp = annotation.validateRegExp();

        if(string.length() < minLength || string.length() > maxLength){
            LOGGER.error(string + " is not valid diu to (length" + minLength + ", " + maxLength + ")");
            throw new RuntimeException(string + " is not valid diu to (length" + minLength + ", " + maxLength + ")");
        }

        if(string.equalsIgnoreCase(forbiddenValue) || string.isEmpty()){
            LOGGER.error(string + " is not valid diu to forbidden value(" + forbiddenValue + ", Empty");
            throw new RuntimeException(string + " is not valid diu to forbidden value(" + forbiddenValue + ", Empty)");
        }

        if(!string.matches(validateRegExp)){
            LOGGER.error(string + " is not valid diu to regexp(" + validateRegExp + ")");
            throw new RuntimeException("("  + string + ") is not valid diu to regexp(" + validateRegExp + ")");
        }

        if(string.contains(annotation.xssPattern())){
            LOGGER.error("message can be not XSS protected");
            throw new RuntimeException("message can be not XSS protected");
        }

    }

    @Override
    public String toString() {
        return getValidatorName();
    }

}
