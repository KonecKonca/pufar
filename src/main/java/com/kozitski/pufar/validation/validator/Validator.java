package com.kozitski.pufar.validation.validator;

import java.io.IOException;
import java.lang.annotation.Annotation;

public interface Validator {

    void validate(Annotation[] annotations, Object object);

    default String getValidatorName() {
        return this.getClass().getSimpleName();
    }

}
