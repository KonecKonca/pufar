package com.kozitski.pufar.validation.validator.primitive;

import com.kozitski.pufar.validation.annotation.primitive.integer.IntValid;
import com.kozitski.pufar.validation.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;

public class IntValidator implements Validator {
    private static Logger LOGGER = LoggerFactory.getLogger(IntValidator.class);

    @Override
    public void validate(Annotation[] annotations, Object object) throws RuntimeException {
        for(Annotation annotation : annotations){
            if(annotation instanceof IntValid && object instanceof Integer){
                intValidation((IntValid) annotation, (Integer) object);
            }
        }
    }

    private void intValidation(IntValid annotation, Integer value) {

        double minValue = annotation.minValue();
        double maxValue = annotation.maxValue();
        if(value < minValue || value > maxValue){
            LOGGER.error("Int value not in range [" + minValue + ", " + maxValue + "]" + "(" + value + ")");
            throw new RuntimeException("Int value not in range [" + minValue + ", " + maxValue + "]" + "(" + value + ")");
        }

    }

    @Override
    public String toString() {
        return getValidatorName();
    }

}
