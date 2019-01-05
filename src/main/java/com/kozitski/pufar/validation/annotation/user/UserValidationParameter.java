package com.kozitski.pufar.validation.annotation.user;

class UserValidationParameter {

    static final long MAX_ID = 1_000_000_000;

    static final int MIN_LOGIN_SIZE = 5;
    static final int MAX_LOGIN_SIZE = 20;

    static final int MIN_PASSWORD_SIZE = 5;
    static final int MAX_PASSWORD_SIZE = 30;

    static final String STRING_FIELD_PATTERN = "[\\w.@а-яА-яёЁ]+";
    static final String XSS_PATTERN = "<script>";

}
