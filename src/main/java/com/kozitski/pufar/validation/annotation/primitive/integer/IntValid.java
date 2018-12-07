package com.kozitski.pufar.validation.annotation.primitive.integer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface IntValid {

    double minValue() default  IntDefaultValidationParameter.MIN_VALUE;
    double maxValue() default  IntDefaultValidationParameter.MAX_VALUE;

}
