package com.kozitski.pufar.validation.annotation.number;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// TODO: Auto-generated Javadoc
/**
 * The Interface NumberValid.
 */
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
