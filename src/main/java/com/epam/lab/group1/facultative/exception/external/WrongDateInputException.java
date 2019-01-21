package com.epam.lab.group1.facultative.exception.external;

public class WrongDateInputException extends IncorrectInputDataException {

    public WrongDateInputException() {
    }

    public WrongDateInputException(String message) {
        super(message);
    }

    public WrongDateInputException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongDateInputException(Throwable cause) {
        super(cause);
    }

    public WrongDateInputException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
