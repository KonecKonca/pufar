package com.kozitski.pufar.validation.annotation.notification;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotificationValid {

    long maxId() default  NotificationDefaultValidationParameter.MAX_ID;

    int minMessageSize() default  NotificationDefaultValidationParameter.MIN_MESSAGE_SIZE;
    int maxMessageSize() default  NotificationDefaultValidationParameter.MAX_MESSAGE_SIZE;

    double minPrice() default  NotificationDefaultValidationParameter.MIN_PRISE;
    double maxPrice() default  NotificationDefaultValidationParameter.MAX_PRISE;

    double minRate() default  NotificationDefaultValidationParameter.MIN_RATE;
    double maxRate() default  NotificationDefaultValidationParameter.MAX_RATE;

    String stringPattern() default NotificationDefaultValidationParameter.STRING_FIELD_PATTERN;
    String xssPattern() default NotificationDefaultValidationParameter.XSS_PATTERN;


}
