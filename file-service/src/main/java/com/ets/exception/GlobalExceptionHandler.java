package com.ets.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;

import static com.ets.exception.ErrorType.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    private ErrorMessage createErrorMessage(Exception exception, ErrorType errorType){
        System.out.println("ERROR: "+exception.getMessage());
        return ErrorMessage.builder()
                .message(errorType.message)
                .code(errorType.getCode())
                .build();

    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handlerRuntimeException(Exception exception){
        System.out.println("Hata oluştu");
        ErrorType errorType = INTERNAL_ERROR;
        return new ResponseEntity<>(createErrorMessage(exception,errorType), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handlerInvalidAuthority(AccessDeniedException exception){
        ErrorType errorType = INVALID_AUTHORITY;
        return new ResponseEntity<>(createErrorMessage(exception,errorType), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FileServiceException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handlerAuthServiceException(FileServiceException exception){
        return new ResponseEntity<>(createErrorMessage(exception,exception.getErrorType()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public final ResponseEntity<ErrorMessage> handleMessageNotReadableException(
            HttpMessageNotReadableException exception) {
        ErrorType errorType = BAD_REQUEST_ERROR;
        return new ResponseEntity<>(createErrorMessage(exception,errorType), errorType.getHttpStatus());
    }

    @ExceptionHandler(InvalidFormatException.class)
    @ResponseBody
    public final ResponseEntity<ErrorMessage> handleInvalidFormatException(
            InvalidFormatException exception) {
        ErrorType errorType = BAD_REQUEST_ERROR;
        return new ResponseEntity<>(createErrorMessage(exception,errorType), errorType.getHttpStatus());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    public final ResponseEntity<ErrorMessage> handlePSQLException(
            DataIntegrityViolationException exception) {
        ErrorType errorType = REGISTER_USED_USERNAME_ERROR;
        return new ResponseEntity<>(createErrorMessage(exception,errorType), errorType.getHttpStatus());
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public final ResponseEntity<ErrorMessage> handleMethodArgumentMisMatchException(
            MethodArgumentTypeMismatchException exception) {

        ErrorType errorType = BAD_REQUEST_ERROR;
        return new ResponseEntity<>(createErrorMessage(exception,errorType), errorType.getHttpStatus());
    }

    @ExceptionHandler(MissingPathVariableException.class)
    @ResponseBody
    public final ResponseEntity<ErrorMessage> handleMethodArgumentMisMatchException(
            MissingPathVariableException exception) {

        ErrorType errorType = BAD_REQUEST_ERROR;
        return new ResponseEntity<>(createErrorMessage(exception,errorType), errorType.getHttpStatus());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public final ResponseEntity<ErrorMessage> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception) {

        ErrorType errorType = BAD_REQUEST_ERROR;
        List<String> fields = new ArrayList<>();
        exception
                .getBindingResult()
                .getFieldErrors()
                .forEach(e -> fields.add(e.getField() + ": " + e.getDefaultMessage()));
        ErrorMessage errorMessage = createErrorMessage(exception,errorType);
        errorMessage.setFields(fields);
        return new ResponseEntity<>(errorMessage, errorType.getHttpStatus());
    }


}
