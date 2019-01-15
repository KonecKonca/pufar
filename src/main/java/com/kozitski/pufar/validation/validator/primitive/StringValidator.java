package com.kozitski.pufar.validation.validator.primitive;

import com.kozitski.pufar.exception.PufarValidationException;
import com.kozitski.pufar.validation.annotation.primitive.string.StringValid;
import com.kozitski.pufar.validation.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;

// TODO: Auto-generated Javadoc
/**
 * The Class StringValidator.
 */
public class StringValidator implements Validator {
    
    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(StringValidator.class);
    
    /**
     * Validate.
     *
     * @param annotations the annotations
     * @param object the object
     * @throws PufarValidationException the pufar validation exception
     */
    @Override
    public void validate(Annotation[] annotations, Object object) throws PufarValidationException {
        for(Annotation annotation : annotations){
            if(annotation instanceof StringValid && object instanceof String){
                stringValidation((StringValid) annotation, (String) object);
            }
        }
    }

    /**
     * String validation.
     *
     * @param annotation the annotation
     * @param string the string
     * @throws PufarValidationException the pufar validation exception
     */
    private void stringValidation(StringValid annotation, String string) throws PufarValidationException {

        if(string == null){
            LOGGER.warn("String can not be null");
            throw new PufarValidationException("String can not be null");
        }

        string = string.trim();

        int minLength = annotation.minLength();
        int maxLength = annotation.maxLength();
        String forbiddenValue = annotation.forbiddenValue();

        String validateRegExp = annotation.validateRegExp();

        if(string.length() < minLength || string.length() > maxLength){
            LOGGER.warn(string + " is not valid diu to (length" + minLength + ", " + maxLength + ")");
            throw new PufarValidationException(string + " is not valid diu to (length" + minLength + ", " + maxLength + ")");
        }

        if(string.equalsIgnoreCase(forbiddenValue) || string.isEmpty()){
            LOGGER.warn(string + " is not valid diu to forbidden value(" + forbiddenValue + ", Empty");
            throw new PufarValidationException(string + " is not valid diu to forbidden value(" + forbiddenValue + ", Empty)");
        }

        if(!string.matches(validateRegExp)){
            LOGGER.warn(string + " is not valid diu to regexp(" + validateRegExp + ")");
            throw new PufarValidationException("("  + string + ") is not valid diu to regexp(" + validateRegExp + ")");
        }

        if(string.toLowerCase().contains(annotation.xssPattern())){
            LOGGER.warn("message can be not XSS protected");
            throw new PufarValidationException("message can be not XSS protected");
        }

    }

    /**
     * To string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return getValidatorName();
    }

}
