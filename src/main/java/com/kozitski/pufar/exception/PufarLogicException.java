package com.kozitski.pufar.exception;

public class PufarLogicException extends Exception {

    public PufarLogicException() {
        super();
    }

    public PufarLogicException(String message) {
        super(message);
    }

    public PufarLogicException(String message, Throwable cause) {
        super(message, cause);
    }

    public PufarLogicException(Throwable cause) {
        super(cause);
    }
}
