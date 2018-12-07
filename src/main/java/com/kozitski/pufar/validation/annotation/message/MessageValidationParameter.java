package com.kozitski.pufar.validation.annotation.message;

class MessageValidationParameter {

    static final int MIN_MESSAGE_SIZE = 0;
    static final int MAX_MESSAGE_SIZE = 1000;

    static final int MIN_LOGIN_SIZE = 4;
    static final int MAX_LOGIN_SIZE = 25;


    static final String STRING_FIELD_PATTERN = "[\\w.@]+";
    static final String XSS_PATTERN = "<script>";

}
