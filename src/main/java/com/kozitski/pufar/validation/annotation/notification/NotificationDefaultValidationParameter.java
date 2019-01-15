package com.kozitski.pufar.validation.annotation.notification;

// TODO: Auto-generated Javadoc
/**
 * The Class NotificationDefaultValidationParameter.
 */
class NotificationDefaultValidationParameter {

    /** The Constant MAX_ID. */
    static final long MAX_ID = 1_000_000_000;
    
    /** The Constant MIN_MESSAGE_SIZE. */
    static final int MIN_MESSAGE_SIZE = 1;
    
    /** The Constant MAX_MESSAGE_SIZE. */
    static final int MAX_MESSAGE_SIZE = 1000;

    /** The Constant MIN_PRISE. */
    static final double MIN_PRISE = 0;
    
    /** The Constant MAX_PRISE. */
    static final double MAX_PRISE = 1_000_000;
    
    /** The Constant MIN_RATE. */
    static final double MIN_RATE = 0;
    
    /** The Constant MAX_RATE. */
    static final double MAX_RATE = 10;

    /** The Constant STRING_FIELD_PATTERN. */
    static final String STRING_FIELD_PATTERN = ".*";
    
    /** The Constant XSS_PATTERN. */
    static final String XSS_PATTERN = "<script>";

    /**
     * Instantiates a new notification default validation parameter.
     */
    private NotificationDefaultValidationParameter() { }

}
