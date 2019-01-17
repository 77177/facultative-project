package com.epam.lab.group1.facultative.exception.internal;

public class UserWithEmailDoesNOtExistException extends EntityNotFoundException {

    public UserWithEmailDoesNOtExistException() {
    }

    public UserWithEmailDoesNOtExistException(String message) {
        super(message);
    }

    public UserWithEmailDoesNOtExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserWithEmailDoesNOtExistException(Throwable cause) {
        super(cause);
    }

    public UserWithEmailDoesNOtExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
