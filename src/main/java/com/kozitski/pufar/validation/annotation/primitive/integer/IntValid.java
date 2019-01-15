package com.kozitski.pufar.validation.annotation.primitive.integer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// TODO: Auto-generated Javadoc
/**
 * The Interface IntValid.
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
