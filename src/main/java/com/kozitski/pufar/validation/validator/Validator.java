package com.kozitski.pufar.validation.validator;

import com.kozitski.pufar.exception.PufarValidationException;

import java.lang.annotation.Annotation;

public interface Validator {

    void validate(Annotation[] annotations, Object object) throws PufarValidationException;

    default String getValidatorName() {
        return this.getClass().getSimpleName();
    }

}
