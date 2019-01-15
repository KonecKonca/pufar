package com.kozitski.pufar.validation.annotation.user;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The Annotation UserValid can marks
 * arguments with type com.kozitski.pufar.user.User.class for
 * validation its. On compilation phase AnnotationValidation
 * engine inlines validation checks.
 * */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface UserValid {

    /**
     * Max id.
     *
     * @return the long
     */
    long maxId() default  UserValidationParameter.MAX_ID;

    /**
     * Min login size.
     *
     * @return the int
     */
    int minLoginSize() default  UserValidationParameter.MIN_LOGIN_SIZE;
    
    /**
     * Max login size.
     *
     * @return the int
     */
    int maxLoginSize() default  UserValidationParameter.MAX_LOGIN_SIZE;

    /**
     * Min password size.
     *
     * @return the int
     */
    int minPasswordSize() default UserValidationParameter.MIN_PASSWORD_SIZE;
    
    /**
     * Max password size.
     *
     * @return the int
     */
    int maxPasswordSize() default UserValidationParameter.MAX_PASSWORD_SIZE;

    /**
     * String pattern.
     *
     * @return the string
     */
    String stringPattern() default UserValidationParameter.STRING_FIELD_PATTERN;
    
    /**
     * Xss pattern.
     *
     * @return the string
     */
    String xssPattern() default UserValidationParameter.XSS_PATTERN;


}
