package com.kozitski.pufar.validation.validator;

import com.kozitski.pufar.entity.user.User;
import com.kozitski.pufar.exception.PufarValidationException;
import com.kozitski.pufar.validation.annotation.user.UserValid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;

public class UserValidator implements Validator {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserValidator.class);

    @Override
    public void validate(Annotation[] annotations, Object object) throws PufarValidationException {
        for(Annotation annotation : annotations){
            if(annotation instanceof UserValid && object instanceof User){
                userValidation((UserValid) annotation, (User) object);
            }
        }
    }
    private void userValidation(UserValid annotation, User user) throws PufarValidationException {

        if(user == null || user.getLogin() == null || user.getPassword() == null || user.getStatus() == null){
            LOGGER.warn("User can not be and contains NULL");
            throw new PufarValidationException("User can not be and contains NULL");
        }

        if(user.getPassword().isEmpty() || user.getLogin().isEmpty()){
            LOGGER.warn("User can not contains EMPTY Strings");
            throw new PufarValidationException("User can not contains EMPTY Strings");
        }

        String trimLogin = user.getLogin().trim();
        String trimPassword = user.getPassword().trim();

        int minLoginSize = annotation.minLoginSize();
        int maxLoginSize = annotation.maxLoginSize();
        int realLoginSize = trimLogin.length();
        if(realLoginSize < minLoginSize || realLoginSize > maxLoginSize){
            LOGGER.warn("login is not in allowed range [" + minLoginSize +  ", " + maxLoginSize + "] (" + realLoginSize + ")");
            throw new PufarValidationException("login is not in allowed range [" + minLoginSize +  ", " + maxLoginSize + "] (" + realLoginSize + ")");
        }

        int minPasswordSize = annotation.minPasswordSize();
        int maxPasswordSize = annotation.maxPasswordSize();
        int realPasswordSize = trimPassword.length();
        if(realPasswordSize < minPasswordSize || realPasswordSize > maxPasswordSize){
            LOGGER.warn("password is not in allowed range [" + minPasswordSize +  ", " + maxPasswordSize + "] (" + realPasswordSize + ")");
            throw new PufarValidationException("password is not in allowed range [" + minPasswordSize +  ", " + maxPasswordSize + "] (" + realPasswordSize + ")");
        }

        long maxId = annotation.maxId();
        long minId = 0;
        long realId = user.getUserId();
        if(realId < minId || realId > maxId){
            LOGGER.warn("id is not in allowed range [" + minId +  ", " + maxId + "] (" + realId + ")");
            throw new PufarValidationException("id is not in allowed range [" + minId +  ", " + maxId + "] (" + realId + ")");
        }

        String xssPattern = annotation.xssPattern();
        if(trimLogin.toLowerCase().contains(xssPattern) || trimPassword.toLowerCase().contains(xssPattern)){
            LOGGER.warn("message can be not XSS protected");
            throw new PufarValidationException("message can be not XSS protected");
        }

        String pattern = annotation.stringPattern();
        if(!trimLogin.matches(pattern)){
            LOGGER.warn("("  + trimLogin + ") is not valid diu to regexp(" + pattern + ")");
            throw new PufarValidationException("("  + trimLogin + ") is not valid diu to regexp(" + pattern + ")");
        }
        if(!trimPassword.matches(pattern)){
            LOGGER.warn("("  + trimPassword + ") is not valid diu to regexp(" + pattern + ")");
            throw new PufarValidationException("("  + trimPassword + ") is not valid diu to regexp(" + pattern + ")");
        }

    }

    @Override
    public String toString() {
        return getValidatorName();
    }

}
