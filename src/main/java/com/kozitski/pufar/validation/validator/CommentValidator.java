package com.kozitski.pufar.validation.validator;

import com.kozitski.pufar.entity.comment.NotificationComment;
import com.kozitski.pufar.exception.PufarValidationException;
import com.kozitski.pufar.validation.annotation.comment.CommentValid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;

public class CommentValidator implements Validator{
    private static final Logger LOGGER = LoggerFactory.getLogger(CommentValidator.class);

    @Override
    public void validate(Annotation[] annotations, Object object) throws PufarValidationException {
        for(Annotation annotation : annotations){
            if(annotation instanceof CommentValid && object instanceof NotificationComment){
                commentValidation((CommentValid) annotation, (NotificationComment) object);
            }
        }
    }

    private void commentValidation(CommentValid annotation, NotificationComment comment) throws PufarValidationException {

        if(comment == null || comment.getComment() == null || comment.getComment().isEmpty()){
            LOGGER.warn("NotificationComment can not be and contains NULL");
            throw new PufarValidationException("NotificationComment can not be and contains NULL");
        }

        String trimComment = comment.getComment().trim();

        int minMessageSize = annotation.minMessageSize();
        int maxMessageSize = annotation.maxMessageSize();
        int realMessageSize = trimComment.length();
        if(realMessageSize < minMessageSize || realMessageSize > maxMessageSize){
            LOGGER.warn("message is not in allowed range [" + minMessageSize +  ", " + maxMessageSize + "] (" + realMessageSize + ")");
            throw new PufarValidationException("message is not in allowed range [" + minMessageSize +  ", " + maxMessageSize + "] (" + realMessageSize + ")");
        }

        String senderLogin = comment.getSenderLogin();
        if(trimComment.toLowerCase().contains(annotation.xssPattern()) || senderLogin.toLowerCase().contains(annotation.xssPattern())){
            LOGGER.warn("message can be not XSS protected");
            throw new PufarValidationException("message can be not XSS protected");
        }

        String pattern = annotation.stringPattern();
        if(!trimComment.matches(pattern)){
            LOGGER.warn("("  + trimComment + ") is not valid diu to regexp(" + pattern + ")");
            throw new PufarValidationException("("  + trimComment + ") is not valid diu to regexp(" + pattern + ")");
        }

    }

    @Override
    public String toString() {
        return getValidatorName();
    }

}
