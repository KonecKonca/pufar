package com.kozitski.pufar.validation.validator.primitive;

import com.kozitski.pufar.exception.PufarValidationException;
import com.kozitski.pufar.validation.annotation.primitive.integer.IntValid;
import com.kozitski.pufar.validation.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;

// TODO: Auto-generated Javadoc
/**
 * The Class IntValidator.
 */
public class IntValidator implements Validator {
    
    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(IntValidator.class);

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
            if(annotation instanceof IntValid && object instanceof Integer){
                intValidation((IntValid) annotation, (Integer) object);
            }
        }
    }

    /**
     * Int validation.
     *
     * @param annotation the annotation
     * @param value the value
     * @throws PufarValidationException the pufar validation exception
     */
    private void intValidation(IntValid annotation, Integer value) throws PufarValidationException {

        double minValue = annotation.minValue();
        double maxValue = annotation.maxValue();
        if(value < minValue || value > maxValue){
            LOGGER.warn("Int value not in range [" + minValue + ", " + maxValue + "]" + "(" + value + ")");
            throw new PufarValidationException("Int value not in range [" + minValue + ", " + maxValue + "]" + "(" + value + ")");
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
