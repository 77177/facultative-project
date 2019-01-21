package com.epam.lab.group1.facultative.exception.external;

public class WrongNameInputException extends IncorrectInputDataException {

    public WrongNameInputException() {
    }

    public WrongNameInputException(String message) {
        super(message);
    }

    public WrongNameInputException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongNameInputException(Throwable cause) {
        super(cause);
    }

    public WrongNameInputException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
