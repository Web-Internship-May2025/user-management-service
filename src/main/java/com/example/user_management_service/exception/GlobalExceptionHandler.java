package com.example.user_management_service.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMsg = "Validation failed";
        ErrorMessage error = new ErrorMessage(errorMsg, HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleResourceNotFound(ResourceNotFoundException ex) {
        ErrorMessage error = new ErrorMessage(ex.getMessage(), HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMessage> handleIllegalArgument(IllegalArgumentException ex) {
        ErrorMessage error = new ErrorMessage(ex.getMessage(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorMessage> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String message = "Invalid parameter: " + ex.getName();
        ErrorMessage error = new ErrorMessage(message, HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorMessage> handleRuntimeException(RuntimeException ex) {
        ErrorMessage error = new ErrorMessage("Internal Server Error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorMessage> handleConstraintViolation(ConstraintViolationException ex) {
        StringBuilder sb = new StringBuilder("Validation errors:");
        ex.getConstraintViolations().forEach(v ->
                sb.append(" [").append(v.getPropertyPath()).append(" ").append(v.getMessage()).append("]")
        );
        ErrorMessage err = new ErrorMessage(sb.toString(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

}
