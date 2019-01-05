package com.kozitski.pufar.validation.validator;

import com.kozitski.pufar.entity.message.UserMessage;
import com.kozitski.pufar.exception.PufarValidationException;
import com.kozitski.pufar.validation.annotation.message.MessageValid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;

public class MessageValidator implements Validator{
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageValidator.class);

    @Override
    public void validate(Annotation[] annotations, Object object) throws PufarValidationException {
        for(Annotation annotation : annotations){
            if(annotation instanceof MessageValid && object instanceof UserMessage){
                messageValidation((MessageValid) annotation, (UserMessage) object);
            }
        }
    }

    private void messageValidation(MessageValid annotation, UserMessage userMessage) throws PufarValidationException {

        if(userMessage == null || userMessage.getDate() == null || userMessage.getMessage() == null
                || userMessage.getSenderLogin() == null || userMessage.getReceiverLogin() == null){

            LOGGER.warn("Notification can not be and contains NULL");
            throw new PufarValidationException("Notification can not be and contains NULL");
        }

        if(userMessage.getReceiverLogin().isEmpty() || userMessage.getSenderLogin().isEmpty() || userMessage.getMessage().isEmpty() ){
            LOGGER.warn("Notification can not contains EMPTY Strings");
            throw new PufarValidationException("Notification can not contains EMPTY Strings");
        }

        String trimMessage = userMessage.getMessage().trim();

        int minMessageSize = annotation.minMessageSize();
        int maxMessageSize = annotation.maxMessageSize();
        int realMessageSize = trimMessage.length();
        if(realMessageSize < minMessageSize || realMessageSize > maxMessageSize){
            LOGGER.warn("message is not in allowed range [" + minMessageSize +  ", " + maxMessageSize + "] (" + realMessageSize + ")");
            throw new PufarValidationException("message is not in allowed range [" + minMessageSize +  ", " + maxMessageSize + "] (" + realMessageSize + ")");
        }

        int minLoginSize = annotation.minLoginSize();
        int maxLoginSize = annotation.maxLoginSize();
        int receiverLoginSize = userMessage.getReceiverLogin().length();
        int senderLoginSize = userMessage.getSenderLogin().length();
        if(receiverLoginSize < minLoginSize || receiverLoginSize > maxLoginSize){
            LOGGER.warn("message is not in allowed range [" + minLoginSize +  ", " + maxLoginSize + "] (" + receiverLoginSize + ")");
            throw new PufarValidationException("message is not in allowed range [" + minLoginSize +  ", " + maxLoginSize + "] (" + receiverLoginSize + ")");
        }
        if(senderLoginSize < minLoginSize || senderLoginSize > maxLoginSize){
            LOGGER.warn("message is not in allowed range [" + minLoginSize +  ", " + maxLoginSize + "] (" + senderLoginSize + ")");
            throw new PufarValidationException("message is not in allowed range [" + minLoginSize +  ", " + maxLoginSize + "] (" + senderLoginSize + ")");
        }

        String senderLogin = userMessage.getSenderLogin();
        String receiverLogin = userMessage.getReceiverLogin();
        String xssPattern = annotation.xssPattern();
        if(trimMessage.toLowerCase().contains(xssPattern) || senderLogin.toLowerCase().contains(xssPattern) || receiverLogin.toLowerCase().contains(xssPattern)){
            LOGGER.warn("message can be not XSS protected");
            throw new PufarValidationException("message can be not XSS protected");
        }

        String pattern = annotation.stringPattern();
        if(!senderLogin.matches(pattern)){
            LOGGER.warn("("  + senderLogin + ") is not valid diu to regexp(" + pattern + ")");
            throw new PufarValidationException("("  + senderLogin + ") is not valid diu to regexp(" + pattern + ")");
        }
        if(!receiverLogin.matches(pattern)){
            LOGGER.warn("("  + receiverLogin + ") is not valid diu to regexp(" + pattern + ")");
            throw new PufarValidationException("("  + receiverLogin + ") is not valid diu to regexp(" + pattern + ")");
        }
        if(!trimMessage.matches(pattern)){
            LOGGER.warn("("  + trimMessage + ") is not valid diu to regexp(" + pattern + ")");
            throw new PufarValidationException("("  + trimMessage + ") is not valid diu to regexp(" + pattern + ")");
        }

    }

    @Override
    public String toString() {
        return getValidatorName();
    }

}
