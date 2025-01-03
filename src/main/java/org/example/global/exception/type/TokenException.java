package org.example.global.exception.type;


import org.example.global.exception.BaseException;
import org.example.global.exception.ExceptionType;

public class TokenException extends BaseException {
    public TokenException(ExceptionType exceptionType){
        super(exceptionType);
    }
}