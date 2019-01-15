package com.kozitski.pufar.validation.validator.primitive;

import com.kozitski.pufar.exception.PufarValidationException;
import com.kozitski.pufar.validation.annotation.primitive.doouble.DoubleValid;
import com.kozitski.pufar.validation.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;

/**
 * The Class DoubleValidator.
 */
public class DoubleValidator implements Validator {
    
    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(DoubleValidator.class);

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
            if(annotation instanceof DoubleValid && object instanceof Double){
                doubleValidation((DoubleValid) annotation, (Double) object);
            }
        }
    }

    /**
     * Double validation.
     *
     * @param annotation the annotation
     * @param value the value
     * @throws PufarValidationException the pufar validation exception
     */
    private void doubleValidation(DoubleValid annotation, double value) throws PufarValidationException{

        double minValue = annotation.minValue();
        double maxValue = annotation.maxValue();
        if(value < minValue || value > maxValue){
            LOGGER.warn("Double value not in range [" + minValue + ", " + maxValue + "]" + "(" + value + ")");
            throw new PufarValidationException("Double value not in range [" + minValue + ", " + maxValue + "]" + "(" + value + ")");
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
