package com.kozitski.pufar.validation.annotation.primitive.doouble;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The Annotation DoubleValid can marks
 * arguments with type double.class for
 * validation its.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface DoubleValid {

    /**
     * Min value.
     *
     * @return the double
     */
    double minValue() default  DoubleDefaultValidationParameter.MIN_VALUE;
    
    /**
     * Max value.
     *
     * @return the double
     */
    double maxValue() default  DoubleDefaultValidationParameter.MAX_VALUE;

}
