package com.kozitski.pufar.validation.annotation.number;

class NumberDefaultValidationParameter {

    static final String COUNTRY_PATTERN = "[\\d+]{3,5}";
    static final String OPERATOR_PATTERN = "[\\d]{2}";
    static final String NUMBER_PATTERN = "[\\d]{7}";

    static final String XSS_PATTERN = "<script>";

    private NumberDefaultValidationParameter() { }

}
