package com.kozitski.pufar.validation.annotation.comment;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The Annotation CommentValid can marks
 * arguments with type com.kozitski.pufar.comment.NotificationComment.class for
 * validation its. On compilation phase AnnotationValidation
 * engine inlines validation checks.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommentValid {

    /**
     * Max id.
     *
     * @return the long
     */
    long maxId() default  CommentValidationParameter.MAX_ID;

    /**
     * Min message size.
     *
     * @return the int
     */
    int minMessageSize() default  CommentValidationParameter.MIN_MESSAGE_SIZE;
    
    /**
     * Max message size.
     *
     * @return the int
     */
    int maxMessageSize() default  CommentValidationParameter.MAX_MESSAGE_SIZE;

    /**
     * String pattern.
     *
     * @return the string
     */
    String stringPattern() default CommentValidationParameter.STRING_FIELD_PATTERN;
    
    /**
     * Xss pattern.
     *
     * @return the string
     */
    String xssPattern() default CommentValidationParameter.XSS_PATTERN;


}
