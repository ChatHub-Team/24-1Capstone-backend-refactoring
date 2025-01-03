package org.example.exception.type;

import org.example.exception.BaseException;
import org.example.exception.ExceptionType;

public class NotCreateException extends BaseException {

    public NotCreateException(ExceptionType exceptionType) {
        super((exceptionType));
    }
}
