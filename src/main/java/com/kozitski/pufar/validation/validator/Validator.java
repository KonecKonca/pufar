package com.kozitski.pufar.validation.validator;

import com.kozitski.pufar.exception.PufarValidationException;

import java.lang.annotation.Annotation;

/**
 * The Interface Validator.
 */
public interface Validator {

    /**
     * Validate.
     *
     * @param annotations the annotations
     * @param object the object
     * @throws PufarValidationException the pufar validation exception
     */
    void validate(Annotation[] annotations, Object object) throws PufarValidationException;

    /**
     * Gets the validator name.
     *
     * @return the validator name
     */
    default String getValidatorName() {
        return this.getClass().getSimpleName();
    }

}
