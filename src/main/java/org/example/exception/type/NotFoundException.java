package org.example.exception.type;

import org.example.exception.BaseException;
import org.example.exception.ExceptionType;

public class NotFoundException extends BaseException {

    public NotFoundException(ExceptionType exceptionType) {
        super((exceptionType));
    }
}
