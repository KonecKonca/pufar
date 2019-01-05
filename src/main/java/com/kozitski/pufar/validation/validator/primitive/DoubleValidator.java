package com.kozitski.pufar.validation.validator.primitive;

import com.kozitski.pufar.exception.PufarValidationException;
import com.kozitski.pufar.validation.annotation.primitive.doouble.DoubleValid;
import com.kozitski.pufar.validation.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;

public class DoubleValidator implements Validator {
    private static final Logger LOGGER = LoggerFactory.getLogger(DoubleValidator.class);

    @Override
    public void validate(Annotation[] annotations, Object object) throws PufarValidationException {
        for(Annotation annotation : annotations){
            if(annotation instanceof DoubleValid && object instanceof Double){
                doubleValidation((DoubleValid) annotation, (Double) object);
            }
        }
    }

    private void doubleValidation(DoubleValid annotation, double value) throws PufarValidationException{

        double minValue = annotation.minValue();
        double maxValue = annotation.maxValue();
        if(value < minValue || value > maxValue){
            LOGGER.warn("Double value not in range [" + minValue + ", " + maxValue + "]" + "(" + value + ")");
            throw new PufarValidationException("Double value not in range [" + minValue + ", " + maxValue + "]" + "(" + value + ")");
        }

    }

    @Override
    public String toString() {
        return getValidatorName();
    }

}
