package com.epam.lab.group1.facultative.exception.internal;

public class UserWithEmailDoesNotExistException extends EntityNotFoundException {

    public UserWithEmailDoesNotExistException() {
    }

    public UserWithEmailDoesNotExistException(String message) {
        super(message);
    }

    public UserWithEmailDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserWithEmailDoesNotExistException(Throwable cause) {
        super(cause);
    }

    public UserWithEmailDoesNotExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
