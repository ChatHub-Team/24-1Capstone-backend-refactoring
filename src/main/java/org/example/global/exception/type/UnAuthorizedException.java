package org.example.global.exception.type;

import org.example.global.exception.BaseException;
import org.example.global.exception.ExceptionType;

public class UnAuthorizedException extends BaseException {

	public UnAuthorizedException(ExceptionType exceptionType) {
		super(exceptionType);
	}
}