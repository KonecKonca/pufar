package com.kozitski.pufar.validation.annotation.user;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface UserValid {

    long maxId() default  UserValidationParameter.MAX_ID;

    int minLoginSize() default  UserValidationParameter.MIN_LOGIN_SIZE;
    int maxLoginSize() default  UserValidationParameter.MAX_LOGIN_SIZE;

    int minPasswordSize() default UserValidationParameter.MIN_PASSWORD_SIZE;
    int maxPasswordSize() default UserValidationParameter.MAX_PASSWORD_SIZE;

    String stringPattern() default UserValidationParameter.STRING_FIELD_PATTERN;
    String xssPattern() default UserValidationParameter.XSS_PATTERN;


}
