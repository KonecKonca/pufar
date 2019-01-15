package com.kozitski.pufar.validation.annotation.primitive.string;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The Annotation StringValid can marks
 * arguments with type java.langString.class for
 * validation its. On compilation phase AnnotationValidation
 * engine inlines validation checks.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface StringValid {

    /**
     * Min length.
     *
     * @return the int
     */
    int minLength() default  StringDefaultValidationParameter.MIN_LENGTH;
    
    /**
     * Max length.
     *
     * @return the int
     */
    int maxLength() default  StringDefaultValidationParameter.MAX_LENGTH;
    
    /**
     * Validate reg exp.
     *
     * @return the string
     */
    String validateRegExp() default  StringDefaultValidationParameter.LOGIN_VALIDATE_PATTERN;
    
    /**
     * Forbidden value.
     *
     * @return the string
     */
    String forbiddenValue() default  StringDefaultValidationParameter.FORBIDDEN_VALUE;

    /**
     * Xss pattern.
     *
     * @return the string
     */
    String xssPattern() default StringDefaultValidationParameter.XSS_PATTERN;

}
