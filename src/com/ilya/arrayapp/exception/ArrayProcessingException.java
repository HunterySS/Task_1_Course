package com.ilya.arrayapp.exception;

public class ArrayProcessingException extends Exception {

    public ArrayProcessingException(String message) {
        super(message);
    }

    public ArrayProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
