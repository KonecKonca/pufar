package com.kozitski.pufar.validation.validator;

import com.kozitski.pufar.entity.message.UserMessage;
import com.kozitski.pufar.validation.annotation.message.MessageValid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;

public class MessageValidator implements Validator{
    private static Logger LOGGER = LoggerFactory.getLogger(MessageValidator.class);

    @Override
    public void validate(Annotation[] annotations, Object object) throws RuntimeException {
        for(Annotation annotation : annotations){
            if(annotation instanceof MessageValid && object instanceof UserMessage){
                messageValidation((MessageValid) annotation, (UserMessage) object);
            }
        }
    }

    private void messageValidation(MessageValid annotation, UserMessage userMessage) {

        if(userMessage == null || userMessage.getTime() == null || userMessage.getMessage() == null
                || userMessage.getSenderLogin() == null || userMessage.getReceiverLogin() == null){

            LOGGER.error("Notification can not be and contains NULL");
            throw new RuntimeException("Notification can not be and contains NULL");
        }

        if(userMessage.getReceiverLogin().isEmpty() || userMessage.getSenderLogin().isEmpty() || userMessage.getMessage().isEmpty() ){
            LOGGER.error("Notification can not contains EMPTY Strings");
            throw new RuntimeException("Notification can not contains EMPTY Strings");
        }

        int minMessageSize = annotation.minMessageSize();
        int maxMessageSize = annotation.maxMessageSize();
        int realMessageSize = userMessage.getMessage().length();
        if(realMessageSize < minMessageSize || realMessageSize > maxMessageSize){
            LOGGER.error("message is not in allowed range [" + minMessageSize +  ", " + maxMessageSize + "] (" + realMessageSize + ")");
            throw new RuntimeException("message is not in allowed range [" + minMessageSize +  ", " + maxMessageSize + "] (" + realMessageSize + ")");
        }

        int minLoginSize = annotation.minLoginSize();
        int maxLoginSize = annotation.maxLoginSize();
        int receiverLoginSize = userMessage.getReceiverLogin().length();
        int senderLoginSize = userMessage.getSenderLogin().length();
        if(receiverLoginSize < minLoginSize || receiverLoginSize > maxLoginSize){
            LOGGER.error("message is not in allowed range [" + minLoginSize +  ", " + maxLoginSize + "] (" + receiverLoginSize + ")");
            throw new RuntimeException("message is not in allowed range [" + minLoginSize +  ", " + maxLoginSize + "] (" + receiverLoginSize + ")");
        }
        if(senderLoginSize < minLoginSize || senderLoginSize > maxLoginSize){
            LOGGER.error("message is not in allowed range [" + minLoginSize +  ", " + maxLoginSize + "] (" + senderLoginSize + ")");
            throw new RuntimeException("message is not in allowed range [" + minLoginSize +  ", " + maxLoginSize + "] (" + senderLoginSize + ")");
        }

        String message = userMessage.getMessage();
        String senderLogin = userMessage.getSenderLogin();
        String receiverLogin = userMessage.getReceiverLogin();
        String xssPattern = annotation.xssPattern();
        if(message.contains(xssPattern) || senderLogin.contains(xssPattern) || receiverLogin.contains(xssPattern)){
            LOGGER.error("message can be not XSS protected");
            throw new RuntimeException("message can be not XSS protected");
        }

        String pattern = annotation.stringPattern();
        if(!senderLogin.matches(pattern)){
            LOGGER.error("("  + senderLogin + ") is not valid diu to regexp(" + pattern + ")");
            throw new RuntimeException("("  + senderLogin + ") is not valid diu to regexp(" + pattern + ")");
        }
        if(!receiverLogin.matches(pattern)){
            LOGGER.error("("  + receiverLogin + ") is not valid diu to regexp(" + pattern + ")");
            throw new RuntimeException("("  + receiverLogin + ") is not valid diu to regexp(" + pattern + ")");
        }
        if(!message.matches(pattern)){
            LOGGER.error("("  + message + ") is not valid diu to regexp(" + pattern + ")");
            throw new RuntimeException("("  + message + ") is not valid diu to regexp(" + pattern + ")");
        }

    }

    @Override
    public String toString() {
        return getValidatorName();
    }

}
