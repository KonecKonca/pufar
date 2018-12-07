package com.kozitski.pufar.exception;

public class PufarServiceException extends Exception{

    public PufarServiceException() {
        super();
    }

    public PufarServiceException(String message) {
        super(message);
    }

    public PufarServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public PufarServiceException(Throwable cause) {
        super(cause);
    }
}
