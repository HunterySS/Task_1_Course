package com.ilya.arrayapp.exception;

public class UnsupportedArrayTypeException extends ArrayProcessingException {

  public UnsupportedArrayTypeException(String message) {
    super(message);
  }

  public UnsupportedArrayTypeException(String message, Throwable cause) {
    super(message, cause);
  }
}