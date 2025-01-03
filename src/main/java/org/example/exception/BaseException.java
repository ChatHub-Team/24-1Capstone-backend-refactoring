package org.example.exception;

public class BaseException extends RuntimeException {

    private final ExceptionType exceptionType;

    public BaseException(final ExceptionType exceptionType) {
        super(exceptionType.getMessage());
        this.exceptionType = exceptionType;
    }
}
