package com.kozitski.pufar.validation.validator;

import com.kozitski.pufar.entity.number.MobilPhoneNumber;
import com.kozitski.pufar.exception.PufarValidationException;
import com.kozitski.pufar.validation.annotation.number.NumberValid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;

/**
 * The Class NumberValidator.
 */
public class NumberValidator implements Validator {
    
    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(NumberValidator.class);

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
            if(annotation instanceof NumberValid && object instanceof MobilPhoneNumber){
                userValidation((NumberValid) annotation, (MobilPhoneNumber) object);
            }
        }
    }
    
    /**
     * User validation.
     *
     * @param annotation the annotation
     * @param phoneNumber the phone number
     * @throws PufarValidationException the pufar validation exception
     */
    private void userValidation(NumberValid annotation, MobilPhoneNumber phoneNumber) throws PufarValidationException {

        String country = phoneNumber.getCountry();
        String operator = phoneNumber.getOperator();
        String number = phoneNumber.getNumber();

        if(country == null || operator == null || number == null){
            LOGGER.warn("MobilPhoneNumber can not be and contains NULL");
            throw new PufarValidationException("MobilPhoneNumber can not be and contains NULL");
        }

        if(country.isEmpty() || operator.isEmpty() || number.isEmpty()){
            LOGGER.warn("MobilPhoneNumber can not contains EMPTY Strings");
            throw new PufarValidationException("MobilPhoneNumber can not contains EMPTY Strings");
        }

        String countryPattern = annotation.countryPattern();
        if(!country.matches(countryPattern)){
            LOGGER.warn("("  + country + ") is not valid diu to regexp(" + countryPattern + ")");
            throw new PufarValidationException("("  + country + ") is not valid diu to regexp(" + countryPattern + ")");
        }

        String operatorPattern = annotation.operatorPattern();
        if(!operator.matches(operatorPattern)){
            LOGGER.warn("("  + operator + ") is not valid diu to regexp(" + operatorPattern + ")");
            throw new PufarValidationException("("  + operator + ") is not valid diu to regexp(" + operatorPattern + ")");
        }

        String numberPattern = annotation.numberPattern();
        if(!number.matches(numberPattern)){
            LOGGER.warn("("  + number + ") is not valid diu to regexp(" + numberPattern + ")");
            throw new PufarValidationException("("  + number + ") is not valid diu to regexp(" + numberPattern + ")");
        }

        String xssPattern = annotation.xssPattern();
        if(country.toLowerCase().contains(xssPattern) || operator.toLowerCase().contains(xssPattern) || number.toLowerCase().contains(xssPattern)){
            LOGGER.warn("Number can be not XSS protected");
            throw new PufarValidationException("Number can be not XSS protected");
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
