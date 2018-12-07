package com.kozitski.pufar.validation.annotation.comment;

class CommentValidationParameter {

    static final long MAX_ID = 1_000_000_000;

    static final int MIN_MESSAGE_SIZE = 0;
    static final int MAX_MESSAGE_SIZE = 1000;
    static final String XSS_PATTERN = "<script>";

    static final String STRING_FIELD_PATTERN = "[\\w.@]+";

}
