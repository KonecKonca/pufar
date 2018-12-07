package com.kozitski.pufar.validation.annotation.primitive.string;

class StringDefaultValidationParameter {

    static final int MIN_LENGTH = 1;
    static final int MAX_LENGTH = 555;
    static final String LOGIN_VALIDATE_PATTERN = ".*";
    // forbidden
    static final String FORBIDDEN_VALUE = "";
    static final String XSS_PATTERN = "<script>";

}
