package org.example.exception.type;

import org.example.exception.BaseException;
import org.example.exception.ExceptionType;

public class BadRequestException extends BaseException {

    public BadRequestException(ExceptionType exceptionType) {
        super(exceptionType);
    }
}
