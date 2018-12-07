package com.kozitski.pufar.exception;

public class PufarDAOException extends Exception {

    public PufarDAOException() {
        super();
    }
    public PufarDAOException(String message) {
        super(message);
    }
    public PufarDAOException(String message, Throwable cause) {
        super(message, cause);
    }
    public PufarDAOException(Throwable cause) {
        super(cause);
    }

}
