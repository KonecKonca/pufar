package com.kozitski.pufar.validation.aspect;

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
import java.util.List;


/**
 * The Class ValidationAspect.
 * Define pointCut from methods which marked
 * by AspectValid.class and start inlining validating advices
 */
@Aspect
public class ValidationAspect {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidationAspect.class);

    /**
     * Validate string.
     *
     * @param joinPoint the join point
     * @throws PufarValidationException the pufar validation exception
     */
    @Before("@annotation(com.kozitski.pufar.validation.annotation.AspectValid)")
    public void validateString(JoinPoint joinPoint) throws PufarValidationException {

        LOGGER.info("validation advice is executing");

        validate(joinPoint);

        LOGGER.info("validation complete successfully");
    }


    /**
     * Validate.
     *
     * @param joinPoint the join point
     * @throws PufarValidationException the pufar validation exception
     */
    private void validate(JoinPoint joinPoint) throws PufarValidationException {

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Annotation[][] annotations = methodSignature.getMethod().getParameterAnnotations();
        Object[] args = joinPoint.getArgs();

        List<Validator> validators = ValidatorRegister.initValidators();

        for (int i = 0; i < args.length; i++) {
            for(Validator validator : validators){
                validator.validate(annotations[i], args[i]);
            }
        }

    }


}
