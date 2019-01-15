package com.kozitski.pufar.validation.annotation.number;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The Annotation NumberValid can marks
 * arguments with type com.kozitski.pufar.number.MobilePhoneNumber.class for
 * validation its. On compilation phase AnnotationValidation
 * engine inlines validation checks.
 * */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface NumberValid {

    /**
     * Country pattern.
     *
     * @return the string
     */
    String countryPattern() default NumberDefaultValidationParameter.COUNTRY_PATTERN;
    
    /**
     * Operator pattern.
     *
     * @return the string
     */
    String operatorPattern() default NumberDefaultValidationParameter.OPERATOR_PATTERN;
    
    /**
     * Number pattern.
     *
     * @return the string
     */
    String numberPattern() default NumberDefaultValidationParameter.NUMBER_PATTERN;

    /**
     * Xss pattern.
     *
     * @return the string
     */
    String xssPattern() default NumberDefaultValidationParameter.XSS_PATTERN;


}
