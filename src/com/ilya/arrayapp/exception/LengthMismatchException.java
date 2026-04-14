package com.ilya.arrayapp.exception;

public class LengthMismatchException extends ArrayProcessingException {

  public LengthMismatchException(String message) {
    super(message);
  }

  public LengthMismatchException(String message, Throwable cause) {
    super(message, cause);
  }
}