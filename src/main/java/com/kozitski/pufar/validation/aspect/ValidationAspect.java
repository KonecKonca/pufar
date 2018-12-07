package com.kozitski.pufar.validation.aspect;

import com.kozitski.pufar.exception.PufarDAOException;
import com.kozitski.pufar.exception.PufarValidationException;
import com.kozitski.pufar.validation.util.ValidatorRegister;
import com.kozitski.pufar.validation.validator.Validator;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.ArrayList;


@Aspect
public class ValidationAspect {
    private static Logger LOGGER = LoggerFactory.getLogger(ValidationAspect.class);


    @Before("@annotation(com.kozitski.pufar.validation.annotation.AspectValid)")
    public void validateString(JoinPoint joinPoint) throws RuntimeException {

        LOGGER.info("validation advice is executing");

        try {
            validate(joinPoint);
        }
        catch (PufarValidationException e) {
            LOGGER.error("Annotation Validation engine is not working", e);
        }

        LOGGER.info("validation complete successfully");
    }


    private void validate(JoinPoint joinPoint) throws PufarValidationException {

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Annotation[][] annotations = methodSignature.getMethod().getParameterAnnotations();
        Object[] args = joinPoint.getArgs();

        ArrayList<Validator> validators = ValidatorRegister.initValidators();

        for (int i = 0; i < args.length; i++) {
            for(Validator validator : validators){
                validator.validate(annotations[i], args[i]);
            }
        }

    }


}
