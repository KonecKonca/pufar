package com.kozitski.pufar.validation.annotation.user;

// TODO: Auto-generated Javadoc
/**
 * The Class UserValidationParameter.
 */
class UserValidationParameter {

    /** The Constant MAX_ID. */
    static final long MAX_ID = 1_000_000_000;

    /** The Constant MIN_LOGIN_SIZE. */
    static final int MIN_LOGIN_SIZE = 5;
    
    /** The Constant MAX_LOGIN_SIZE. */
    static final int MAX_LOGIN_SIZE = 20;

    /** The Constant MIN_PASSWORD_SIZE. */
    static final int MIN_PASSWORD_SIZE = 5;
    
    /** The Constant MAX_PASSWORD_SIZE. */
    static final int MAX_PASSWORD_SIZE = 30;

    /** The Constant STRING_FIELD_PATTERN. */
    static final String STRING_FIELD_PATTERN = ".+";
    
    /** The Constant XSS_PATTERN. */
    static final String XSS_PATTERN = "<script>";

    /**
     * Instantiates a new user validation parameter.
     */
    private UserValidationParameter() { }
}
