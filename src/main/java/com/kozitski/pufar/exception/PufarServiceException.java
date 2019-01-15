package com.kozitski.pufar.exception;

/**
 * The Class PufarServiceException.
 */
public class PufarServiceException extends Exception {

    /**
     * Instantiates a new pufar service exception.
     */
    public PufarServiceException() {
        super();
    }

    /**
     * Instantiates a new pufar service exception.
     *
     * @param message the message
     */
    public PufarServiceException(String message) {
        super(message);
    }

    /**
     * Instantiates a new pufar service exception.
     *
     * @param message the message
     * @param cause the cause
     */
    public PufarServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new pufar service exception.
     *
     * @param cause the cause
     */
    public PufarServiceException(Throwable cause) {
        super(cause);
    }

}
