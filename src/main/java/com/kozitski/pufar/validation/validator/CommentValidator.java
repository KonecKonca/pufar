package com.kozitski.pufar.validation.validator;

import com.kozitski.pufar.entity.comment.NotificationComment;
import com.kozitski.pufar.validation.annotation.comment.CommentValid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;

public class CommentValidator implements Validator{
    private static Logger LOGGER = LoggerFactory.getLogger(CommentValidator.class);

    @Override
    public void validate(Annotation[] annotations, Object object) throws RuntimeException {
        for(Annotation annotation : annotations){
            if(annotation instanceof CommentValid && object instanceof NotificationComment){
                commentValidation((CommentValid) annotation, (NotificationComment) object);
            }
        }
    }

    private void commentValidation(CommentValid annotation, NotificationComment comment) {

        if(comment == null || comment.getComment() == null || comment.getComment().isEmpty()){
            LOGGER.error("NotificationComment can not be and contains NULL");
            throw new RuntimeException("NotificationComment can not be and contains NULL");
        }

        int minMessageSize = annotation.minMessageSize();
        int maxMessageSize = annotation.maxMessageSize();
        int realMessageSize = comment.getComment().length();
        if(realMessageSize < minMessageSize || realMessageSize > maxMessageSize){
            LOGGER.error("message is not in allowed range [" + minMessageSize +  ", " + maxMessageSize + "] (" + realMessageSize + ")");
        }

        String realMessage = comment.getComment();
        if(realMessage.contains(annotation.xssPattern())){
            LOGGER.error("message can be not XSS protected");
            throw new RuntimeException("message can be not XSS protected");
        }

        String pattern = annotation.stringPattern();
        if(!realMessage.matches(pattern)){
            LOGGER.error("("  + realMessage + ") is not valid diu to regexp(" + pattern + ")");
            throw new RuntimeException("("  + realMessage + ") is not valid diu to regexp(" + pattern + ")");
        }


        long commentId = comment.getCommentId();
        long userId = comment.getUserId();
        long maxId = annotation.maxId();
        long minId = 0;

        if(commentId < 0 || userId < 0 || commentId > maxId || userId > maxId){
            LOGGER.error("id is not in allowed range [" + minId +  ", " + maxId + "] (" + commentId + ", "  + userId + ")");
            throw new RuntimeException("id is not in allowed range [" + minId +  ", " + maxId + "] (" + commentId + ", "  + userId + ")");
        }

    }

    @Override
    public String toString() {
        return getValidatorName();
    }

}
