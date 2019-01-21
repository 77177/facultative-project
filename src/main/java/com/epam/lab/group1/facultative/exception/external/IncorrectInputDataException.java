package com.epam.lab.group1.facultative.exception.external;

/**
 * Thorwn when input from user does not valid.
 */
public class IncorrectInputDataException extends RuntimeException {

    public IncorrectInputDataException() {
    }

    public IncorrectInputDataException(String message) {
        super(message);
    }

    public IncorrectInputDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectInputDataException(Throwable cause) {
        super(cause);
    }

    public IncorrectInputDataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
