package com.kozitski.pufar.validation.annotation.comment;

/**
 * The Class CommentValidationParameter.
 */
class CommentValidationParameter {

    /** The Constant MAX_ID. */
    static final long MAX_ID = 1_000_000_000;

    /** The Constant MIN_MESSAGE_SIZE. */
    static final int MIN_MESSAGE_SIZE = 0;
    
    /** The Constant MAX_MESSAGE_SIZE. */
    static final int MAX_MESSAGE_SIZE = 1000;
    
    /** The Constant XSS_PATTERN. */
    static final String XSS_PATTERN = "<script>";

    /** The Constant STRING_FIELD_PATTERN. */
    static final String STRING_FIELD_PATTERN = ".+";

    /**
     * Instantiates a new comment validation parameter.
     */
    private CommentValidationParameter() { }
}
