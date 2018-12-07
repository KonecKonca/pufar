package com.kozitski.pufar.validation.validator;

import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.validation.annotation.user.UserValid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;

public class UserValidator implements Validator {
    private static Logger LOGGER = LoggerFactory.getLogger(UserValidator.class);

    @Override
    public void validate(Annotation[] annotations, Object object) throws RuntimeException {
        for(Annotation annotation : annotations){
            if(annotation instanceof UserValid && object instanceof User){
                userValidation((UserValid) annotation, (User) object);
            }
        }
    }
    private void userValidation(UserValid annotation, User user) {

        if(user == null || user.getLogin() == null || user.getPassword() == null || user.getStatus() == null){
            LOGGER.error("User can not be and contains NULL");
            throw new RuntimeException("User can not be and contains NULL");
        }

        if(user.getPassword().isEmpty() || user.getLogin().isEmpty()){
            LOGGER.error("User can not contains EMPTY Strings");
            throw new RuntimeException("User can not contains EMPTY Strings");
        }

        int minLoginSize = annotation.minLoginSize();
        int maxLoginSize = annotation.maxLoginSize();
        int realLoginSize = user.getLogin().length();
        if(realLoginSize < minLoginSize || realLoginSize > maxLoginSize){
            LOGGER.error("login is not in allowed range [" + minLoginSize +  ", " + maxLoginSize + "] (" + realLoginSize + ")");
            throw new RuntimeException("login is not in allowed range [" + minLoginSize +  ", " + maxLoginSize + "] (" + realLoginSize + ")");
        }

        int minPasswordSize = annotation.minPasswordSize();
        int maxPasswordSize = annotation.maxPasswordSize();
        int realPasswordSize = user.getPassword().length();
        if(realPasswordSize < minPasswordSize || realPasswordSize > maxPasswordSize){
            LOGGER.error("password is not in allowed range [" + minPasswordSize +  ", " + maxPasswordSize + "] (" + realPasswordSize + ")");
            throw new RuntimeException("password is not in allowed range [" + minPasswordSize +  ", " + maxPasswordSize + "] (" + realPasswordSize + ")");
        }

        long maxId = annotation.maxId();
        long minId = 0;
        long realId = user.getUserId();
        if(realId < minId || realId > maxId){
            LOGGER.error("id is not in allowed range [" + minId +  ", " + maxId + "] (" + realId + ")");
            throw new RuntimeException("id is not in allowed range [" + minId +  ", " + maxId + "] (" + realId + ")");
        }

        String realLogin = user.getLogin();
        String realPassword = user.getPassword();
        String xssPattern = annotation.xssPattern();
        if(realLogin.contains(xssPattern) || realPassword.contains(xssPattern)){
            LOGGER.error("message can be not XSS protected");
            throw new RuntimeException("message can be not XSS protected");
        }

        String pattern = annotation.stringPattern();
        if(!realLogin.matches(pattern)){
            LOGGER.error("("  + realLogin + ") is not valid diu to regexp(" + pattern + ")");
            throw new RuntimeException("("  + realLogin + ") is not valid diu to regexp(" + pattern + ")");
        }
        if(!realPassword.matches(pattern)){
            LOGGER.error("("  + realPassword + ") is not valid diu to regexp(" + pattern + ")");
            throw new RuntimeException("("  + realPassword + ") is not valid diu to regexp(" + pattern + ")");
        }

    }

    @Override
    public String toString() {
        return getValidatorName();
    }

}
