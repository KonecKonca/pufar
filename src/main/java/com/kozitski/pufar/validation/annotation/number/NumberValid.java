package com.kozitski.pufar.validation.annotation.number;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface NumberValid {

    String countryPattern() default NumberDefaultValidationParameter.COUNTRY_PATTERN;
    String operatorPattern() default NumberDefaultValidationParameter.OPERATOR_PATTERN;
    String numberPattern() default NumberDefaultValidationParameter.NUMBER_PATTERN;

    String xssPattern() default NumberDefaultValidationParameter.XSS_PATTERN;


}
