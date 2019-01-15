package com.kozitski.pufar.validation.annotation.notification;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The Annotation NotificationValid can marks
 * arguments with type com.kozitski.pufar.notification.Notification.class for
 * validation its. On compilation phase AnnotationValidation
 * engine inlines validation checks.
 * */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotificationValid {

    /**
     * Max id.
     *
     * @return the long
     */
    long maxId() default  NotificationDefaultValidationParameter.MAX_ID;

    /**
     * Min message size.
     *
     * @return the int
     */
    int minMessageSize() default  NotificationDefaultValidationParameter.MIN_MESSAGE_SIZE;
    
    /**
     * Max message size.
     *
     * @return the int
     */
    int maxMessageSize() default  NotificationDefaultValidationParameter.MAX_MESSAGE_SIZE;

    /**
     * Min price.
     *
     * @return the double
     */
    double minPrice() default  NotificationDefaultValidationParameter.MIN_PRISE;
    
    /**
     * Max price.
     *
     * @return the double
     */
    double maxPrice() default  NotificationDefaultValidationParameter.MAX_PRISE;

    /**
     * Min rate.
     *
     * @return the double
     */
    double minRate() default  NotificationDefaultValidationParameter.MIN_RATE;
    
    /**
     * Max rate.
     *
     * @return the double
     */
    double maxRate() default  NotificationDefaultValidationParameter.MAX_RATE;

    /**
     * String pattern.
     *
     * @return the string
     */
    String stringPattern() default NotificationDefaultValidationParameter.STRING_FIELD_PATTERN;
    
    /**
     * Xss pattern.
     *
     * @return the string
     */
    String xssPattern() default NotificationDefaultValidationParameter.XSS_PATTERN;


}
