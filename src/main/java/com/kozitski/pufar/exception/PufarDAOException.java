package com.kozitski.pufar.exception;

/**
 * The Class PufarDAOException.
 */
public class PufarDAOException extends Exception {

    /**
     * Instantiates a new pufar DAO exception.
     */
    public PufarDAOException() {
        super();
    }

    /**
     * Instantiates a new pufar DAO exception.
     *
     * @param message the message
     */
    public PufarDAOException(String message) {
        super(message);
    }

    /**
     * Instantiates a new pufar DAO exception.
     *
     * @param message the message
     * @param cause the cause
     */
    public PufarDAOException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new pufar DAO exception.
     *
     * @param cause the cause
     */
    public PufarDAOException(Throwable cause) {
        super(cause);
    }

}
