package com.ilya.arrayapp.exception;

public class NullArrayException extends ArrayProcessingException {

    public NullArrayException(String message) {
        super(message);
    }

    public NullArrayException(String message, Throwable cause) {
        super(message, cause);
    }
}