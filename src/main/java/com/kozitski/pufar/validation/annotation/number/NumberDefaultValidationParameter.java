package com.kozitski.pufar.validation.annotation.number;

// TODO: Auto-generated Javadoc
/**
 * The Class NumberDefaultValidationParameter.
 */
class NumberDefaultValidationParameter {

    /** The Constant COUNTRY_PATTERN. */
    static final String COUNTRY_PATTERN = "[\\d+]{3,5}";
    
    /** The Constant OPERATOR_PATTERN. */
    static final String OPERATOR_PATTERN = "[\\d]{2}";
    
    /** The Constant NUMBER_PATTERN. */
    static final String NUMBER_PATTERN = "[\\d]{7}";

    /** The Constant XSS_PATTERN. */
    static final String XSS_PATTERN = "<script>";

    /**
     * Instantiates a new number default validation parameter.
     */
    private NumberDefaultValidationParameter() { }

}
