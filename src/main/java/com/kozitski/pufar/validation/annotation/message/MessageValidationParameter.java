package com.kozitski.pufar.validation.annotation.message;

// TODO: Auto-generated Javadoc
/**
 * The Class MessageValidationParameter.
 */
class MessageValidationParameter {

    /** The Constant MIN_MESSAGE_SIZE. */
    static final int MIN_MESSAGE_SIZE = 0;
    
    /** The Constant MAX_MESSAGE_SIZE. */
    static final int MAX_MESSAGE_SIZE = 1000;

    /** The Constant MIN_LOGIN_SIZE. */
    static final int MIN_LOGIN_SIZE = 4;
    
    /** The Constant MAX_LOGIN_SIZE. */
    static final int MAX_LOGIN_SIZE = 25;


    /** The Constant STRING_FIELD_PATTERN. */
    static final String STRING_FIELD_PATTERN = ".+";
    
    /** The Constant XSS_PATTERN. */
    static final String XSS_PATTERN = "<script>";

    /**
     * Instantiates a new message validation parameter.
     */
    private MessageValidationParameter() { }
}
