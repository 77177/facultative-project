package com.epam.lab.group1.facultative.exception.external;

import com.epam.lab.group1.facultative.exception.external.IncorrectInputDataException;

public class CourseTitleAlreadyExistsException extends IncorrectInputDataException {

    public CourseTitleAlreadyExistsException() {
    }

    public CourseTitleAlreadyExistsException(String message) {
        super(message);
    }

    public CourseTitleAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public CourseTitleAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    public CourseTitleAlreadyExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
