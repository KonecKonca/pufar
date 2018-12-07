package com.kozitski.pufar.validation.annotation.primitive.string;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface StringValid {

    int minLength() default  StringDefaultValidationParameter.MIN_LENGTH;
    int maxLength() default  StringDefaultValidationParameter.MAX_LENGTH;
    String validateRegExp() default  StringDefaultValidationParameter.LOGIN_VALIDATE_PATTERN;
    String forbiddenValue() default  StringDefaultValidationParameter.FORBIDDEN_VALUE;

    String xssPattern() default StringDefaultValidationParameter.XSS_PATTERN;

}
