package com.epam.lab.group1.facultative.exception.internal;

public class EntityNotFound extends RuntimeException {

    public EntityNotFound() {
    }

    public EntityNotFound(String message) {
        super(message);
    }

    public EntityNotFound(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityNotFound(Throwable cause) {
        super(cause);
    }

    public EntityNotFound(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
