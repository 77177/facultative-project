package com.epam.lab.group1.facultative.exception.internal;

import com.epam.lab.group1.facultative.exception.internal.EntityNotFound;

public class UserWithIdDoesNotExist extends EntityNotFound {

    public UserWithIdDoesNotExist() {
    }

    public UserWithIdDoesNotExist(String message) {
        super(message);
    }

    public UserWithIdDoesNotExist(String message, Throwable cause) {
        super(message, cause);
    }

    public UserWithIdDoesNotExist(Throwable cause) {
        super(cause);
    }

    public UserWithIdDoesNotExist(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
