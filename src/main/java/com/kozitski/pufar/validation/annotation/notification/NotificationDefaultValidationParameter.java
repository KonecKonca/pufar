package com.kozitski.pufar.validation.annotation.notification;

class NotificationDefaultValidationParameter {

    static final long MAX_ID = 1_000_000_000;
    static final int MIN_MESSAGE_SIZE = 1;
    static final int MAX_MESSAGE_SIZE = 1000;

    static final double MIN_PRISE = 0;
    static final double MAX_PRISE = 1_000_000;
    static final double MIN_RATE = 0;
    static final double MAX_RATE = 10;

    static final String STRING_FIELD_PATTERN = "[\\s\\w.@а-яА-яёЁ]*";
    static final String XSS_PATTERN = "<script>";

}
