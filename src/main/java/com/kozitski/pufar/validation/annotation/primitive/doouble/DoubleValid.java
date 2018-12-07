package com.kozitski.pufar.validation.annotation.primitive.doouble;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface DoubleValid {

    double minValue() default  DoubleDefaultValidationParameter.MIN_VALUE;
    double maxValue() default  DoubleDefaultValidationParameter.MAX_VALUE;

}
