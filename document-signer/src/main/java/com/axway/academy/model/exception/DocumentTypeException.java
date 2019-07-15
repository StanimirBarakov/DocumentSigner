package com.axway.academy.model.exception;

public class DocumentTypeException extends Exception {
    public DocumentTypeException() {
    }

    public DocumentTypeException(String message) {
        super(message);
    }

    public DocumentTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public DocumentTypeException(Throwable cause) {
        super(cause);
    }

    public DocumentTypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
