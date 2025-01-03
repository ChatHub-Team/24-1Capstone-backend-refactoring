package org.example.global.exception.type;

import org.example.global.exception.BaseException;
import org.example.global.exception.ExceptionType;

public class BadRequestException extends BaseException {

    public BadRequestException(ExceptionType exceptionType) {
        super(exceptionType);
    }
}
