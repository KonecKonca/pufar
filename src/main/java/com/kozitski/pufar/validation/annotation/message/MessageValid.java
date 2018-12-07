package com.kozitski.pufar.validation.annotation.message;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface MessageValid {

    int minMessageSize() default  MessageValidationParameter.MIN_MESSAGE_SIZE;
    int maxMessageSize() default  MessageValidationParameter.MAX_MESSAGE_SIZE;

    int minLoginSize() default  MessageValidationParameter.MIN_LOGIN_SIZE;
    int maxLoginSize() default  MessageValidationParameter.MAX_LOGIN_SIZE;

    String stringPattern() default MessageValidationParameter.STRING_FIELD_PATTERN;
    String xssPattern() default MessageValidationParameter.XSS_PATTERN;

}
