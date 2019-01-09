package com.kozitski.pufar.validation.annotation.message;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// TODO: Auto-generated Javadoc
/**
 * The Interface MessageValid.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface MessageValid {

    /**
     * Min message size.
     *
     * @return the int
     */
    int minMessageSize() default  MessageValidationParameter.MIN_MESSAGE_SIZE;
    
    /**
     * Max message size.
     *
     * @return the int
     */
    int maxMessageSize() default  MessageValidationParameter.MAX_MESSAGE_SIZE;

    /**
     * Min login size.
     *
     * @return the int
     */
    int minLoginSize() default  MessageValidationParameter.MIN_LOGIN_SIZE;
    
    /**
     * Max login size.
     *
     * @return the int
     */
    int maxLoginSize() default  MessageValidationParameter.MAX_LOGIN_SIZE;

    /**
     * String pattern.
     *
     * @return the string
     */
    String stringPattern() default MessageValidationParameter.STRING_FIELD_PATTERN;
    
    /**
     * Xss pattern.
     *
     * @return the string
     */
    String xssPattern() default MessageValidationParameter.XSS_PATTERN;

}
