package com.epam.fifthtask.exception;

public class LogisticBaseException extends Exception {

    public LogisticBaseException() {
        super();
    }

    public LogisticBaseException(String message) {
        super(message);
    }

    public LogisticBaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
