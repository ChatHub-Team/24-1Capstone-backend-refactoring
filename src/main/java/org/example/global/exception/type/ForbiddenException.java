package org.example.global.exception.type;


import org.example.global.exception.BaseException;
import org.example.global.exception.ExceptionType;

public class ForbiddenException extends BaseException {

    public ForbiddenException(ExceptionType exceptionType) {
        super(exceptionType);
    }

}
