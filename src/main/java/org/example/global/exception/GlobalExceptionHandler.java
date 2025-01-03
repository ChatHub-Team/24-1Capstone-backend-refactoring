package org.example.global.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.example.global.exception.response.ErrorResponse;
import org.example.global.exception.response.ExceptionResponse;
import org.example.global.exception.type.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleException(final Exception e) {
        log.error("[" + e.getClass() + "] : " + e.getMessage());
        return ResponseEntity.internalServerError()
                .body(ExceptionResponse.of(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
    }

    //valid 검증
    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(
            final MethodArgumentNotValidException e
    ) {

        BindingResult bindingResult = e.getBindingResult();
        List<ObjectError> objectErrors = bindingResult.getAllErrors();
        List<String> errorMessages = new ArrayList<>();
        for (ObjectError objectError : objectErrors) {
            errorMessages.add(objectError.getDefaultMessage());
        }
        String errorMessage = String.join(" ", errorMessages);
        return ResponseEntity.badRequest()
                .body(ExceptionResponse.of(HttpStatus.BAD_REQUEST.value(), errorMessage));
    }

    //requestParam 검증
    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> constraintViolationException(final ConstraintViolationException e) {
        final List<ErrorResponse> errorResponses = e.getConstraintViolations()
                .stream()
                .map(error -> new ErrorResponse(error.getPropertyPath().toString(), error.getMessage()))
                .toList();
        log.warn("[" + e.getClass() + "] " + errorResponses);
        return ResponseEntity.badRequest()
                .body(ExceptionResponse.of(HttpStatus.BAD_REQUEST.value(), errorResponses.toString()));
    }

    //requestPart 검증
    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> unAuthorizedException(final MissingServletRequestPartException e) {

        return ResponseEntity.badRequest()
                .body(ExceptionResponse.of(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }

    // login 검증
    @ExceptionHandler(SignInException.class)
    public ResponseEntity<?> handleSignInException(SignInException e) {
        return ResponseEntity.badRequest()
                .body(ExceptionResponse.of(e.getExceptionType(), e.data));
    }

    // 인증 처리 검증
    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> unAuthorizedException(final UnAuthorizedException e) {

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ExceptionResponse.from(e.getExceptionType()));
    }

    // request 검증
    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleBadRequestException(final BadRequestException e) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ExceptionResponse.from(e.getExceptionType()));
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleForbiddenException(final ForbiddenException e) {

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ExceptionResponse.from(e.getExceptionType()));
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleNotFoundException(final NotFoundException e) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ExceptionResponse.from(e.getExceptionType()));
    }
}
