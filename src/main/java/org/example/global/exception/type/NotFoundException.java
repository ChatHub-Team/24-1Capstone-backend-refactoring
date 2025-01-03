package org.example.global.exception.type;

import org.example.global.exception.BaseException;
import org.example.global.exception.ExceptionType;

public class NotFoundException extends BaseException {

    public NotFoundException(ExceptionType exceptionType) {
        super((exceptionType));
    }
}
