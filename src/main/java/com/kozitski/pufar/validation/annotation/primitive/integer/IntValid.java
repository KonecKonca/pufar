package com.kozitski.pufar.validation.annotation.primitive.integer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The Annotation IntValid can marks
 * arguments with type int.class for
 * validation its.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface IntValid {

    /**
     * Min value.
     *
     * @return the double
     */
    double minValue() default  IntDefaultValidationParameter.MIN_VALUE;
    
    /**
     * Max value.
     *
     * @return the double
     */
    double maxValue() default  IntDefaultValidationParameter.MAX_VALUE;

}
