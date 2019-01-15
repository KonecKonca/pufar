package com.kozitski.pufar.validation.annotation.primitive.string;

/**
 * The Class StringDefaultValidationParameter.
 */
class StringDefaultValidationParameter {

    /** The Constant MIN_LENGTH. */
    static final int MIN_LENGTH = 1;
    
    /** The Constant MAX_LENGTH. */
    static final int MAX_LENGTH = 555;
    
    /** The Constant LOGIN_VALIDATE_PATTERN. */
    static final String LOGIN_VALIDATE_PATTERN = ".*";

    /** The Constant FORBIDDEN_VALUE. */
    static final String FORBIDDEN_VALUE = "";
    
    /** The Constant XSS_PATTERN. */
    static final String XSS_PATTERN = "<script>";

    /**
     * Instantiates a new string default validation parameter.
     */
    private StringDefaultValidationParameter() { }

}
