package com.kozitski.pufar.validation.annotation.comment;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommentValid {

    long maxId() default  CommentValidationParameter.MAX_ID;

    int minMessageSize() default  CommentValidationParameter.MIN_MESSAGE_SIZE;
    int maxMessageSize() default  CommentValidationParameter.MAX_MESSAGE_SIZE;

    String stringPattern() default CommentValidationParameter.STRING_FIELD_PATTERN;
    String xssPattern() default CommentValidationParameter.XSS_PATTERN;


}
