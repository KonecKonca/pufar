package com.kozitski.pufar.validation.validator;

import com.kozitski.pufar.entity.notification.Notification;
import com.kozitski.pufar.exception.PufarValidationException;
import com.kozitski.pufar.validation.annotation.notification.NotificationValid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;

public class NotificationValidator implements Validator {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationValidator.class);

    @Override
    public void validate(Annotation[] annotations, Object object) throws PufarValidationException {
        for(Annotation annotation : annotations){
            if(annotation instanceof NotificationValid && object instanceof Notification){
                notificationValidation((NotificationValid) annotation, (Notification) object);
            }
        }
    }
    private void notificationValidation(NotificationValid annotation, Notification notification) throws PufarValidationException {

        if(notification == null || notification.getMessage() == null || notification.getMessage().isEmpty()){
            LOGGER.warn("Notification can not be and contains NULL");
            throw new PufarValidationException("Notification can not be and contains NULL");
        }

        long maxId = annotation.maxId();
        long realUserId = notification.getUserId();
        long realNotificationId = notification.getNotificationId();
        if(realUserId > maxId || realNotificationId > maxId){
            LOGGER.warn("id bigger than allowed [" + maxId + "] (" + realUserId + ", " + realNotificationId + ")");
            throw new PufarValidationException("id bigger than allowed [" + maxId + "] (" + realUserId + ", " + realNotificationId + ")");
        }
        if(realUserId <= 0 || realNotificationId <= 0){
            LOGGER.warn("id can not be negative (" + realNotificationId + ")");
            throw new PufarValidationException("id can not be negative (" + realNotificationId + ")");
        }

        int messageMinSize = annotation.minMessageSize();
        int messageMaxSize = annotation.maxMessageSize();
        int realMessageSize = notification.getMessage().length();
        if(realMessageSize < messageMinSize || realMessageSize > messageMaxSize){
            LOGGER.warn("message is not in allowed range [" + messageMinSize +  ", " + messageMaxSize + "] (" + realMessageSize + ")");
            throw new PufarValidationException("message is not in allowed range [" + messageMinSize +  ", " + messageMaxSize + "] (" + realMessageSize + ")");
        }

        double minPrice = annotation.minPrice();
        double maxPrice = annotation.maxPrice();
        double realPrice = notification.getPrice();
        if(realPrice < minPrice || realPrice > maxPrice){
            LOGGER.warn("price is not in allowed range [" + minPrice +  ", " + maxPrice + "] (" + realPrice + ")");
            throw new PufarValidationException("price is not in allowed range [" + minPrice +  ", " + maxPrice + "] (" + realPrice + ")");
        }


        double minRate = annotation.minRate();
        double maxRate = annotation.maxRate();
        double realRate = notification.getRate();
        if(realRate < minRate || realRate > maxRate){
            LOGGER.warn("rate is not in allowed range [" + minRate +  ", " + maxRate + "] (" + realRate + ")");
            throw new PufarValidationException("rate is not in allowed range [" + minRate +  ", " + maxRate + "] (" + realRate + ")");
        }

        if(notification.getUnit() == null){
            LOGGER.warn("Unit can not be NULL");
            throw  new RuntimeException("Unit can not be NULL");
        }

        String realMessage = notification.getMessage();
        if(realMessage.toLowerCase().contains(annotation.xssPattern())){
            LOGGER.warn("message can be not XSS protected");
            throw new PufarValidationException("message can be not XSS protected");
        }

        String pattern = annotation.stringPattern();
        if(!realMessage.matches(pattern)){
            LOGGER.warn("("  + realMessage + ") is not valid diu to regexp(" + pattern + ")");
            throw new PufarValidationException("("  + realMessage + ") is not valid diu to regexp(" + pattern + ")");
        }

    }

    @Override
    public String toString() {
        return getValidatorName();
    }

}
