package com.epam.lab.group1.facultative.exception.internal;

public class UserWithIdDoesNotExistException extends EntityNotFoundException {

    public UserWithIdDoesNotExistException() {
    }

    public UserWithIdDoesNotExistException(String message) {
        super(message);
    }

    public UserWithIdDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserWithIdDoesNotExistException(Throwable cause) {
        super(cause);
    }

    public UserWithIdDoesNotExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
