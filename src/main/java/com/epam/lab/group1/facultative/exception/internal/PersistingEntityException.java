package com.epam.lab.group1.facultative.exception.internal;

public class PersistingEntityException extends RuntimeException {

    public PersistingEntityException() {
    }

    public PersistingEntityException(String message) {
        super(message);
    }

    public PersistingEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public PersistingEntityException(Throwable cause) {
        super(cause);
    }

    public PersistingEntityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
