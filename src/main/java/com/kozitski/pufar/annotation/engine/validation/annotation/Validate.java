package com.kozitski.pufar.annotation.engine.validation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Validate{

    Class type() default String.class;

    int minLength() default  DefaultValidationParameter.MIN_LENGTH;
    int maxLength() default  DefaultValidationParameter.MAX_LENGTH;
    String validateRegExp() default  DefaultValidationParameter.LOGIN_VALIDATE_PATTERN;
    String forbiddenValue() default  DefaultValidationParameter.FORBIDDEN_VALUE;

}
